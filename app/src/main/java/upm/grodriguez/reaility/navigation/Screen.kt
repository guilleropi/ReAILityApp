package upm.grodriguez.reaility.navigation

enum class ScreenEnum {
    SIGN_IN_SCREEN,
    SIGN_UP_SCREEN,
    VERIFY_EMAIL_SCREEN,
    FORGOT_PASSWORD_SCREEN,
    PROFILE_SCREEN,
    EDIT_PROFILE_SCREEN,
    MAP_SCREEN,
    NEW_CREATION_SCREEN,
    VIEW_RESULT_SCREEN,
    CREATION_SCREEN
}

sealed class Screen(val route: String) {
    object SignInScreen: Screen(ScreenEnum.SIGN_IN_SCREEN.name)
    object ForgotPasswordScreen: Screen(ScreenEnum.FORGOT_PASSWORD_SCREEN.name)
    object SignUpScreen: Screen(ScreenEnum.SIGN_UP_SCREEN.name)
    object VerifyEmailScreen: Screen(ScreenEnum.VERIFY_EMAIL_SCREEN.name)
    object ProfileScreen: Screen(ScreenEnum.PROFILE_SCREEN.name)
    object EditProfileScreen: Screen(ScreenEnum.EDIT_PROFILE_SCREEN.name)
    object MapScreen: Screen(ScreenEnum.MAP_SCREEN.name)
    object NewCreationScreen: Screen(ScreenEnum.NEW_CREATION_SCREEN.name)
    object ViewResultScreen: Screen(ScreenEnum.VIEW_RESULT_SCREEN.name)
    object CreationScreen: Screen(ScreenEnum.CREATION_SCREEN.name)
}