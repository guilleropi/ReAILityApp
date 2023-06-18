package upm.grodriguez.reaility.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import upm.grodriguez.reaility.navigation.Screen.*
import upm.grodriguez.reaility.presentation.creation_view.CreationScreen
import upm.grodriguez.reaility.presentation.forgot_password.ForgotPasswordScreen
import upm.grodriguez.reaility.presentation.profile.ProfileScreen
import upm.grodriguez.reaility.presentation.edit_profile.EditProfileScreen
import upm.grodriguez.reaility.presentation.sign_in.SignInScreen
import upm.grodriguez.reaility.presentation.sign_up.SignUpScreen
import upm.grodriguez.reaility.presentation.verify_email.VerifyEmailScreen
import upm.grodriguez.reaility.presentation.map.MapScreen
import upm.grodriguez.reaility.presentation.new_creation.NewCreationScreen
import upm.grodriguez.reaility.presentation.new_creation.NewCreationViewModel
import upm.grodriguez.reaility.presentation.view_result.ViewResultScreen

@OptIn(DelicateCoroutinesApi::class)
@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController
) {
    val newCreationViewModel: NewCreationViewModel = hiltViewModel()

    var fullScreenImage by remember { mutableStateOf(false) }

    AnimatedNavHost(
        navController = navController,
        startDestination = SignInScreen.route,
        enterTransition = {EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = SignInScreen.route
        ) {
            SignInScreen(
                navigateToForgotPasswordScreen = {
                    navController.navigate(ForgotPasswordScreen.route)
                },
                navigateToSignUpScreen = {
                    navController.navigate(SignUpScreen.route)
                }
            )
        }
        composable(
            route = ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = SignUpScreen.route
        ) {
            SignUpScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = VerifyEmailScreen.route
        ) {
            VerifyEmailScreen(
                navigateToMapScreen = {
                    navController.navigate(MapScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                navigateEditProfile = {
                    navController.navigate(EditProfileScreen.route)
                }
            )
        }
        composable(
            route = MapScreen.route
        ) {
            MapScreen(
                navigateToNewCreation = { navController.navigate(NewCreationScreen.route) },
                navigateToProfile = { navController.navigate(ProfileScreen.route) },
                navigateToLogin = {
                    navController.navigate(SignInScreen.route)
                },
                navigateToCreation = { creationID ->
                    GlobalScope.launch(Dispatchers.Main) {
                        navController.navigate("${CreationScreen.route}/$creationID")
                    }
                }
            )
        }
        composable(
            route = ProfileScreen.route
        ) {
            ProfileScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateEditProfile = {
                    navController.navigate(EditProfileScreen.route)
                }
            )
        }
        composable(
            route = EditProfileScreen.route
        ) {
            EditProfileScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = NewCreationScreen.route
        ) {
            NewCreationScreen(
                viewModel = newCreationViewModel,
                navigateBack = {
                    navController.popBackStack()
                },
                viewPromptResult = {
                    navController.navigate(ViewResultScreen.route)
                }
            )
        }
        composable(
            route = ViewResultScreen.route
        ) {
            ViewResultScreen(
                viewModel = newCreationViewModel,
                navigateBack = {
                    GlobalScope.launch(Dispatchers.Main) {
                        navController.navigate(MapScreen.route)
                    }
                },
                navigateToCreationCallback = { creationID ->
                    navController.navigate("${CreationScreen.route}/$creationID")
                },
                imageFullScreen = fullScreenImage,
                fullScreenCallback = {
                    fullScreenImage = true
                }
            )
            BackHandler {
                if (fullScreenImage)
                {
                    fullScreenImage = false
                }
                // else not possible to go back, just press "Discard" button
            }
        }

        composable(
            route = "${CreationScreen.route}/{creationID}"
        ) { navBackStackEntry ->
            CreationScreen(
                creationID = navBackStackEntry.arguments?.getString("creationID") ?: "",
                navigateBack = {
                    navController.navigate(MapScreen.route)
                },
                fullScreenImage = fullScreenImage,
                fullScreenCallback = {
                    fullScreenImage = true
                }
            )
            BackHandler {
                if (fullScreenImage)
                {
                    fullScreenImage = false
                }
                else
                {
                    navController.navigate(MapScreen.route)
                }
            }
        }
    }
}