package da.zo.rickandmortyapi.characters.data.utils

import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

//
// Created by DaZo20 on 28/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
fun ChipGroup.getTextChipChecked(): String {
    val selectedId: Int = this.checkedChipId
    return if (selectedId > -1) findViewById<Chip>(selectedId).text.toString() else ""
}

fun ChipGroup.setChipChecked(selectedId: Int) {
    if (selectedId >0) this.findViewById<Chip>(selectedId).isCheckable = true
}

fun RadioGroup.getTextRadioButtonChecked(): String {
    val selectedId: Int = this.checkedRadioButtonId
    return if (selectedId > -1) findViewById<RadioButton>(selectedId).text.toString() else ""
}

fun RadioGroup.setRadioButtonChecked(selectedId: Int) {
    if (selectedId > 0) findViewById<RadioButton>(selectedId).isChecked = true
}
