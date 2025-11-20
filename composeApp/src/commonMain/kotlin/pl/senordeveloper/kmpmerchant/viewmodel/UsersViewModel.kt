package pl.senordeveloper.kmpmerchant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.senordeveloper.kmpmerchant.database.dao.UserDao
import pl.senordeveloper.kmpmerchant.database.entities.UserEntity
import pl.senordeveloper.kmpmerchant.network.dto.User
import pl.senordeveloper.kmpmerchant.network.services.UserService
import saschpe.log4k.Log

class UsersViewModel(
    val userService: UserService,
    val userDao: UserDao
) : ViewModel() {

    // create mutable state flow
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> =
        _state
            .onStart { loadUsers() }
            .onEach { Log.debug {
                "state: $it"
            } }
            .stateIn(viewModelScope, WhileSubscribed(5_000), State())

    init {
        viewModelScope.launch {
            userDao.getAll().collect {
                users ->
                Log.debug {
                    "Collected ${users.size} users from database"
                }
                _state.update { state ->
                    state.copy(users = users.map { User
                        .fromEntity(it)})
                }
            }
        }
    }


    fun loadUsers() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }
            userService.getUsers().onRight {
                viewModelScope.launch {
                    userDao.insert(it.users.map {
                        UserEntity.from(it)
                    })
                }

                _state.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
                .onLeft {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val users: List<User> = emptyList(),
    )
}
