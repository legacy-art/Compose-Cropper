package com.smarttoolfactory.cropper.settings.frames.edit

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.smarttoolfactory.cropper.R
import com.smarttoolfactory.cropper.model.*

@Composable
fun CropFrameAddDialog(
    aspectRatio: AspectRatio,
    cropFrame: CropFrame,
    onConfirm: (CropFrame) -> Unit,
    onDismiss: () -> Unit
) {

    val dstBitmap = ImageBitmap.imageResource(id = R.drawable.landscape2)

    val outlineType = cropFrame.outlineType

    var outline: CropOutline by remember {
        mutableStateOf(cropFrame.copy().outlines[0])
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            when (outlineType) {
                OutlineType.RoundedRect -> {

                    val shape = outline as RoundedCornerCropShape

                    RoundedCornerCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        roundedCornerCropShape = shape
                    ) {
                        outline = it
                    }
                }
                OutlineType.CutCorner -> {
                    val shape = outline as CutCornerCropShape

                    CutCornerCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        cutCornerCropShape = shape
                    ) {
                        outline = it
                    }
                }
                OutlineType.Oval -> {

                    val shape = outline as OvalCropShape

                    OvalCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        ovalCropShape = shape
                    ) {
                        outline = it
                    }
                }
                OutlineType.Polygon -> {

                    val shape = outline as PolygonCropShape

                    PolygonCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        polygonCropShape = shape
                    ) {
                        outline = it
                    }
                }
                else -> Unit
            }
        },
        confirmButton = {
            Button(onClick = {

                val newOutlines: List<CropOutline> = cropFrame.outlines
                    .toMutableList()
                    .apply {
                        add(outline)
                    }
                    .toList()

                val newCropFrame = cropFrame.copy(
                    cropOutlineContainer = getOutlineContainer(
                        outlineType = outlineType,
                        index = newOutlines.size - 1,
                        outlines = newOutlines
                    )
                )

                onConfirm(newCropFrame)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    )
}