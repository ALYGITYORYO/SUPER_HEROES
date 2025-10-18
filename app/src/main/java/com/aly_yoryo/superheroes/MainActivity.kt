package com.aly_yoryo.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.aly_yoryo.superheroes.Data.SuperData
import com.aly_yoryo.superheroes.Model.Hero
import com.aly_yoryo.superheroes.ui.theme.SuperheroesTheme

import androidx.compose.foundation.lazy.items // <-- ¡ASEGÚRATE DE ESTA IMPORTACIÓN!
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    init()
                }
            }
        }
    }
}

@Composable
fun init(){
    Contain()
}
@Composable
fun Contain(){
    LazyColumn {
        items(SuperData.heroesList) { hero ->
            // El bloque 'it' ahora es 'hero', un elemento Hero individual
            // Puedes pasar este 'hero' a tu componente de tarjeta (Card)
            SuperItem(hero)
        }
    }
}

@Composable
fun SuperItem(heroe: Hero,
              modifier: Modifier = Modifier) {
    //ete sirve como un a bandera para desplegar y cerrar el card
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
    )
    val horizontalPadding = dimensionResource(id = R.dimen.padding_medium)
    val verticalPadding = dimensionResource(id = R.dimen.padding_small)
    val paddingLarge = dimensionResource(id = R.dimen.image_size)

    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupa todo el ancho disponible
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        shape = RoundedCornerShape(8.dp), // Radio de esquina de la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Elevación de la tarjeta

    ) {
        Row(
            modifier = Modifier
                .background(color = color)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Sección de Texto (Nombre y Descripción)
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio restante después de la imagen
            ) {
                Text(
                    text = stringResource(heroe.nameRes),
                    style = MaterialTheme.typography.displaySmall // O el estilo que uses para el nombre
                )
                Text(
                    text = stringResource(heroe.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge // O el estilo que uses para la descripción
                )
            }

            // Espacio entre texto e imagen (si es necesario)
            Spacer(modifier = Modifier.size(16.dp)) // Espaciador de 16dp

            // Sección de Imagen
            Image(
                painter = painterResource(heroe.imageRes),
                contentDescription = stringResource(heroe.nameRes), // Descripción para accesibilidad
                modifier = Modifier
                    .size(72.dp) // Tamaño de la imagen
                    .clip(RoundedCornerShape(8.dp)), // Radio de esquina para la imagen
                contentScale = ContentScale.Crop // Recorta la imagen para que llene el espacio
            )
        }
    }
}
@Composable
fun SuperIcon(
    @DrawableRes imagen: Int,
    modifier: Modifier = Modifier
){
    val imageSize = dimensionResource(id = R.dimen.image_size)

    Image(
        modifier = modifier
            .size(imageSize)
            .padding(start = 1.dp)
            .clip(MaterialTheme.shapes.small),
        painter = painterResource(imagen),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun SuperInfo(
    @StringRes nombre: Int,
    @StringRes descripcion: Int,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            text = stringResource(nombre),
            //style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
        )
        Text(
            text = stringResource(descripcion),
            //style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview
@Composable
private fun superVista() {
    SuperheroesTheme (darkTheme = false){
        Contain()
    }

}