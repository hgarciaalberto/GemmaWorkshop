import SwiftUI

@main
struct iOSApp: App {

    init() {
        AppModuleKt.doInitKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}