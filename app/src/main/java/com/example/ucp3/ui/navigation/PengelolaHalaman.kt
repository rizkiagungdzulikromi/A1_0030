package com.example.ucp3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp3.ui.view.AnggotaTim.AnggotaDetailScreen
import com.example.ucp3.ui.view.AnggotaTim.AnggotaEntryScreen
import com.example.ucp3.ui.view.AnggotaTim.AnggotaHomeScreen
import com.example.ucp3.ui.view.AnggotaTim.AnggotaUpdateScreen
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaDetail
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaEntry
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaHome
import com.example.ucp3.ui.view.AnggotaTim.DestinasiAnggotaUpdate
import com.example.ucp3.ui.view.DestinasiHome
import com.example.ucp3.ui.view.Proyek.DestinasiDetail
import com.example.ucp3.ui.view.Proyek.DestinasiEntry
import com.example.ucp3.ui.view.Proyek.DestinasiUpdate
import com.example.ucp3.ui.view.Proyek.ProyekDetailScreen
import com.example.ucp3.ui.view.Proyek.ProyekEntryScreen
import com.example.ucp3.ui.view.Proyek.ProyekUpdateScreen
import com.example.ucp3.ui.view.ProyekHomeScreen
import com.example.ucp3.ui.view.Tim.DestinasiTimDetail
import com.example.ucp3.ui.view.Tim.DestinasiTimEntry
import com.example.ucp3.ui.view.Tim.DestinasiTimHome
import com.example.ucp3.ui.view.Tim.DestinasiTimUpdate
import com.example.ucp3.ui.view.Tim.TimDetailScreen
import com.example.ucp3.ui.view.Tim.TimEntryScreen
import com.example.ucp3.ui.view.Tim.TimHomeScreen
import com.example.ucp3.ui.view.Tim.TimUpdateScreen
import com.example.ucp3.ui.view.Tugas.DestinasiTugasDetail
import com.example.ucp3.ui.view.Tugas.DestinasiTugasEntry
import com.example.ucp3.ui.view.Tugas.DestinasiTugasHome
import com.example.ucp3.ui.view.Tugas.DestinasiTugasUpdate
import com.example.ucp3.ui.view.Tugas.TugasDetailScreen
import com.example.ucp3.ui.view.Tugas.TugasEntryScreen
import com.example.ucp3.ui.view.Tugas.TugasHomeScreen
import com.example.ucp3.ui.view.Tugas.TugasUpdateScreen


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ){
        composable(DestinasiHome.route){
            ProyekHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToTim = { navController.navigate(DestinasiTimHome.route) },
                navigateToTugas = { navController.navigate(DestinasiTugasHome.route) },
                navigateToAnggota = { navController.navigate(DestinasiAnggotaHome.route) },

                onDetailClick = { idProyek ->
                    navController.navigate("${DestinasiDetail.route}/$idProyek")
                }
            )
        }
        composable(DestinasiEntry.route){
            ProyekEntryScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.IDPROYEK) {
            type = NavType.IntType }
        )
        ){
            val idProyek = it.arguments?.getInt(DestinasiDetail.IDPROYEK)
            idProyek?.let { idProyek ->
                ProyekDetailScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$idProyek") },
                    navigateBack = { navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.IDPROYEK){
            type = NavType.IntType
        }
        )
        ){
            val idProyek = it.arguments?.getInt(DestinasiUpdate.IDPROYEK)
            idProyek?.let { idProyek ->
                ProyekUpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        //pengelola Tim
        composable(DestinasiTimHome.route) {
            TimHomeScreen(
                navigateToTimEntry = {navController.navigate(DestinasiTimEntry.route)},
                onDetailClick = { idTim ->
                    navController.navigate("${DestinasiTimDetail.route}/$idTim")
                },
                navigateHomeBack = {navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route) }
                }
            )
        }
        composable(DestinasiTimEntry.route) {
            TimEntryScreen(navigateBack = {
                navController.navigate(DestinasiTimHome.route) {
                    popUpTo(DestinasiTimHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiTimDetail.routesWithArg, arguments = listOf(navArgument(DestinasiTimDetail.IDTIM){
            type = NavType.StringType
        })) {
            val idTim = it.arguments?.getString(DestinasiTimDetail.IDTIM)
            idTim?.let { idTim ->
                TimDetailScreen(
                    navigateToTimUpdate = {navController.navigate("${DestinasiTimUpdate.route}/$idTim")},
                    navigateBack = {navController.navigate(DestinasiTimHome.route){
                        popUpTo(DestinasiTimHome.route) {inclusive = true}
                    } }
                )
            }
        }
        composable(DestinasiTimUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiTimUpdate.IDTIM){
            type = NavType.IntType
        })) {
            val idTim = it.arguments?.getInt(DestinasiTimUpdate.IDTIM)
            idTim?.let { idTim->
                TimUpdateScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }
        
        //pengelola anggota
        composable(DestinasiAnggotaHome.route) {
            AnggotaHomeScreen(
                navigateToAnggotaEntry = {navController.navigate(DestinasiAnggotaEntry.route)},
                onDetailClick = { idAnggota ->
                    navController.navigate("${DestinasiAnggotaDetail.route}/$idAnggota")
                },
                navigateHomeBack = {navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route) }
                }
            )
        }
        composable(DestinasiAnggotaEntry.route) {
            AnggotaEntryScreen(navigateBack = {
                navController.navigate(DestinasiAnggotaHome.route) {
                    popUpTo(DestinasiAnggotaHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiAnggotaDetail.routesWithArg, arguments = listOf(navArgument(DestinasiAnggotaDetail.IDANGGOTA){
            type = NavType.IntType
        })) {
            val idAnggota = it.arguments?.getInt(DestinasiAnggotaDetail.IDANGGOTA)
            idAnggota?.let { idAnggota ->
                AnggotaDetailScreen(
                    navigateToAnggotaUpdate = {navController.navigate("${DestinasiAnggotaUpdate.route}/$idAnggota")},
                    navigateBack = {navController.navigate(DestinasiAnggotaHome.route){
                        popUpTo(DestinasiAnggotaHome.route) {inclusive = true}
                    } }
                )
            }
        }
        composable(DestinasiAnggotaUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiAnggotaUpdate.IDANGGOTA){
            type = NavType.IntType
        })) {
            val idAnggota = it.arguments?.getInt(DestinasiAnggotaUpdate.IDANGGOTA)
            idAnggota?.let { idAnggota ->
                AnggotaUpdateScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }

        //pengelola Tugas
        composable(DestinasiTugasHome.route) {
            TugasHomeScreen(
                navigateToTugasEntry = {navController.navigate(DestinasiTugasEntry.route)},
                onDetailClick = { idTugas ->
                    navController.navigate("${DestinasiTugasDetail.route}/$idTugas")
                },
                navigateHomeBack = {navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route) }
                }
            )
        }
        composable(DestinasiTugasEntry.route) {
            TugasEntryScreen(navigateBack = {
                navController.navigate(DestinasiTugasHome.route) {
                    popUpTo(DestinasiTugasHome.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiTugasDetail.routesWithArg, arguments = listOf(navArgument(DestinasiTugasDetail.IDTUGAS){
            type = NavType.IntType
        })) {
            val idTugas = it.arguments?.getInt(DestinasiTugasDetail.IDTUGAS)
            idTugas?.let { idTugas ->
                TugasDetailScreen(
                    navigateToTugasUpdate = {navController.navigate("${DestinasiTugasUpdate.route}/$idTugas")},
                    navigateBack = {navController.navigate(DestinasiTugasHome.route){
                        popUpTo(DestinasiTugasHome.route) {inclusive = true}
                    } }
                )
            }
        }
        composable(DestinasiTugasUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiTugasUpdate.IDTUGAS){
            type = NavType.IntType
        })) {
            val idTugas = it.arguments?.getInt(DestinasiTugasUpdate.IDTUGAS)
            idTugas?.let { idTugas ->
                TugasUpdateScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.popBackStack()}
                )
            }
        }
    }
}