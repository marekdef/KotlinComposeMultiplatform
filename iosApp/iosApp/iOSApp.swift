import SwiftUI

import SharedWithYou

import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
