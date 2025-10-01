package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            /**
             * We collect events on the main dispatcher, but then immediately switch
             * to the Main.immediate dispatcher to ensure that the onEvent lambda
             * is invoked synchronously if possible, preventing potential issues
             * with state updates or navigation happening slightly delayed.
             *
             * However, for most UI events, collecting directly on Dispatchers.Main
             * (which repeatOnLifecycle often uses by default for its coroutine scope)
             * or Main.immediate within the collect block is fine.
             * The key is that repeatOnLifecycle handles cancellation.
             */
            withContext(Dispatchers.Main.immediate) { // Or simply Dispatchers.Main
                flow.collect { event ->
                    onEvent(event)
                }
            }
        }
    }
}