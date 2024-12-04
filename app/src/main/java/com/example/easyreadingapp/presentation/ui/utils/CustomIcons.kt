package com.example.easyreadingapp.presentation.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Lock: ImageVector
    get() {
        if (_Lock != null) {
            return _Lock!!
        }
        _Lock = ImageVector.Builder(
            name = "Lock",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(240f, 880f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(160f, 800f)
                verticalLineToRelative(-400f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(240f, 320f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-80f)
                quadToRelative(0f, -83f, 58.5f, -141.5f)
                reflectiveQuadTo(480f, 40f)
                reflectiveQuadToRelative(141.5f, 58.5f)
                reflectiveQuadTo(680f, 240f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(40f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(800f, 400f)
                verticalLineToRelative(400f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(720f, 880f)
                close()
                moveToRelative(0f, -80f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(-400f)
                horizontalLineTo(240f)
                close()
                moveToRelative(240f, -120f)
                quadToRelative(33f, 0f, 56.5f, -23.5f)
                reflectiveQuadTo(560f, 600f)
                reflectiveQuadToRelative(-23.5f, -56.5f)
                reflectiveQuadTo(480f, 520f)
                reflectiveQuadToRelative(-56.5f, 23.5f)
                reflectiveQuadTo(400f, 600f)
                reflectiveQuadToRelative(23.5f, 56.5f)
                reflectiveQuadTo(480f, 680f)
                moveTo(360f, 320f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(-80f)
                quadToRelative(0f, -50f, -35f, -85f)
                reflectiveQuadToRelative(-85f, -35f)
                reflectiveQuadToRelative(-85f, 35f)
                reflectiveQuadToRelative(-35f, 85f)
                close()
                moveTo(240f, 800f)
                verticalLineToRelative(-400f)
                close()
            }
        }.build()
        return _Lock!!
    }

private var _Lock: ImageVector? = null

public val Library: ImageVector
    get() {
        if (_Library != null) {
            return _Library!!
        }
        _Library = ImageVector.Builder(
            name = "Library",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(5f, 2.5f)
                lineToRelative(0.5f, -0.5f)
                horizontalLineToRelative(2f)
                lineToRelative(0.5f, 0.5f)
                verticalLineToRelative(11f)
                lineToRelative(-0.5f, 0.5f)
                horizontalLineToRelative(-2f)
                lineToRelative(-0.5f, -0.5f)
                verticalLineToRelative(-11f)
                close()
                moveTo(6f, 3f)
                verticalLineToRelative(10f)
                horizontalLineToRelative(1f)
                verticalLineTo(3f)
                horizontalLineTo(6f)
                close()
                moveToRelative(3.171f, 0.345f)
                lineToRelative(0.299f, -0.641f)
                lineToRelative(1.88f, -0.684f)
                lineToRelative(0.64f, 0.299f)
                lineToRelative(3.762f, 10.336f)
                lineToRelative(-0.299f, 0.641f)
                lineToRelative(-1.879f, 0.684f)
                lineToRelative(-0.64f, -0.299f)
                lineTo(9.17f, 3.345f)
                close()
                moveToRelative(1.11f, 0.128f)
                lineToRelative(3.42f, 9.396f)
                lineToRelative(0.94f, -0.341f)
                lineToRelative(-3.42f, -9.397f)
                lineToRelative(-0.94f, 0.342f)
                close()
                moveTo(1f, 2.5f)
                lineToRelative(0.5f, -0.5f)
                horizontalLineToRelative(2f)
                lineToRelative(0.5f, 0.5f)
                verticalLineToRelative(11f)
                lineToRelative(-0.5f, 0.5f)
                horizontalLineToRelative(-2f)
                lineToRelative(-0.5f, -0.5f)
                verticalLineToRelative(-11f)
                close()
                moveTo(2f, 3f)
                verticalLineToRelative(10f)
                horizontalLineToRelative(1f)
                verticalLineTo(3f)
                horizontalLineTo(2f)
                close()
            }
        }.build()
        return _Library!!
    }

private var _Library: ImageVector? = null