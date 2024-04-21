package com.softland.codelandtask

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.softland.codelandtask.ui.theme.CodelandTaskTheme
import com.softland.codelandtask.ui.theme.secondButton
import com.softland.codelandtask.ui.theme.themeColor
import com.softland.codelandtask.ui.theme.uiScreens.Screens
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodelandTaskTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize() ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.splash.route
    ) {
        composable(route = Screens.splash.route) {
            SplashScreen (navController = navController)
        }
        composable(route = Screens.loginScreen.route) {
            LoginScreen (navController = navController)
        }
        composable(route = Screens.homeScreen.route) {
            HomeScreen (navController = navController)
        }

    }

}


@Composable
fun HomeScreen(navController: NavHostController,modifier: Modifier=Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf("") }
    val sharedPreferences =  context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val stroke = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
    val painter: Painter = rememberImagePainter(imageUri.toString())
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
         if (uri.toString().isNotEmpty()) {
             sharedPreferences.edit().apply {
                 putString("Image", uri.toString())
             }.apply()
         }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (toTop,toMiddle,toBottom) = createRefs()
            Text(
                modifier = modifier
                    .wrapContentWidth()
                    .constrainAs(toTop) {
                        top.linkTo(parent.top , margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "Upload a Image",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                textAlign = TextAlign.Start
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(toMiddle) {
                        top.linkTo(toTop.bottom , margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 32.dp)
                    .height(509.dp)
                    .drawBehind {
                        drawRoundRect(color = Color.Black , style = stroke)
                    } ,
                contentAlignment = Alignment.Center ,
            ) {
                Image(
                    painter = if (imageUri.isNotEmpty()){painter} else{painterResource(id = R.drawable.img)} ,
                    contentDescription = "preview",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center)
            }
            Box(modifier = modifier
                .constrainAs(toBottom) {
                    top.linkTo(toMiddle.bottom , margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(horizontal = 24.dp , vertical = 40.dp),
                contentAlignment = Alignment.BottomCenter){
                Row(modifier = modifier.fillMaxWidth()){
                    Box(modifier = modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart) {
                        Button(modifier = Modifier
                            .width(119.dp)
                            .height(56.dp) ,
                            onClick = {
                                launcher.launch("image/*")
                            },
                            shape = RoundedCornerShape(10.dp) ,
                            colors = ButtonDefaults.buttonColors(containerColor = themeColor)) {
                            Text("Upload")
                        }

                    }
                    Box(modifier = modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd) {
                        Button(modifier = Modifier
                            .width(119.dp)
                            .height(56.dp) ,
                            onClick = {
                              imageUri= sharedPreferences.getString("Image","").toString()
                            },
                            shape = RoundedCornerShape(10.dp) ,
                            colors = ButtonDefaults.buttonColors(containerColor = secondButton)) {
                            Text("View",
                                color = Color.Black)
                        }

                    }
                }
            }
        }
 }

}


@Composable
fun LoginScreen(navController: NavHostController,modifier: Modifier=Modifier) {
    var userName by remember { mutableStateOf( "") }
    var password by remember { mutableStateOf( "") }
       val context = LocalContext.current
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (toTop,toMiddle,toSecondLast,toBottom) = createRefs()
            Box(modifier = modifier
                .height(329.dp)
                .width(210.dp) .constrainAs(toTop) {
                    top.linkTo(parent.top , margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                contentAlignment = Alignment.TopCenter) {
                Image(
                    painter = painterResource(id = R.drawable.login) ,
                    contentDescription = "splash",
                    modifier = Modifier.fillMaxSize())
            }
            Column(modifier = Modifier.constrainAs(toMiddle) {
                top.linkTo(toTop.bottom , margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = userName,
                        onValueChange = {
                            if (it.length<9){
                                userName = it
                            } },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        label = {
                            Text(text = "Username")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ) ,
                    )
                    Spacer(modifier = modifier.padding(top = 20.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            if (it.length<9){
                                password = it
                            } },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        label = {
                            Text(text = "Company Code ")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done/* specify IME action */
                        ),)
                    Spacer(modifier = modifier.padding(top = 20.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 30.dp),
                        onClick = {
                                  if (userName.isEmpty()){
                                     Toast.makeText(context,"Fill Username Filed!",Toast.LENGTH_SHORT).show()

                                  }else if (password.isEmpty()){
                                      Toast.makeText(context,"Fill Password Filed!",Toast.LENGTH_SHORT).show()
                                  }else{
                                      navController.popBackStack()
                                      navController.navigate(Screens.homeScreen.route)
                                      Toast.makeText(context,"Login Success!",Toast.LENGTH_SHORT).show()
                                  }
                        },
                        shape = RoundedCornerShape(10.dp) ,
                        colors = ButtonDefaults.buttonColors(containerColor = themeColor)) {
                        Text("Login")
                    }


            }

            Column(modifier = modifier
                .fillMaxWidth()
                .padding(start = 40.dp).constrainAs(toSecondLast) {
                    top.linkTo(toMiddle.bottom , margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "For Assistance & Login Details Contact:",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                    textAlign = TextAlign.Start
                )
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier.wrapContentWidth(),
                        text = "English, Kannada & Telugu ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = modifier.wrapContentWidth(),
                        text = ": 7406333800",
                        fontSize = 12.sp,
                        color = themeColor,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                        textAlign = TextAlign.Start
                    )
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier.wrapContentWidth(),
                        text = "English, Kannada & Hindi   ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = modifier.wrapContentWidth(),
                        text = " : 9071386515",
                        fontSize = 12.sp,
                        color = themeColor,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                        textAlign = TextAlign.Start
                    )
                }

            }
            Box(modifier = Modifier
                .fillMaxWidth().constrainAs(toBottom) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 25.dp)
                },
                contentAlignment = Alignment.BottomCenter) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "v1.7 © 2023 Codeland Infosolutions Pvt Ltd.",
                    fontSize = 12.sp,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    ),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)) ,
                    textAlign = TextAlign.Center
                )

            }

        }
    }
}



@Composable
fun SplashScreen(navController: NavHostController,modifier: Modifier=Modifier) {
    LaunchedEffect(key1 = true) {
        delay(800)
        navController.popBackStack()
        navController.navigate(Screens.loginScreen.route)
    }

  Box(modifier = modifier
      .fillMaxSize()
      .background(themeColor),
      contentAlignment = Alignment.Center) {
      Card(modifier = Modifier
          .width(224.dp)
          .height(320.dp)
          .border(1.dp , Color.Gray , shape = RoundedCornerShape(63.dp)),
          shape = RoundedCornerShape(63.dp) ,
          elevation = CardDefaults.cardElevation(
              defaultElevation = 10.dp
          )
      ) {
          Column(modifier = modifier.fillMaxSize().background(Color.White),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally) {
              Box(modifier = modifier
                  .height(180.dp)
                  .width(180.dp)) {
                  Image(
                      painter = painterResource(id = R.drawable.codelandlogo) ,
                      contentDescription = "splash",
                      modifier = Modifier.fillMaxSize())

              }
              Box(modifier = modifier
                  .height(91.dp)
                  .width(144.dp)) {
                  Image(
                      painter = painterResource(id = R.drawable.txt) ,
                      contentDescription = "splash",
                      modifier = Modifier.fillMaxSize())

              }
          }

      }
  }
}
