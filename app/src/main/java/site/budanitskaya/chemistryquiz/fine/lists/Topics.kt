package site.budanitskaya.chemistryquiz.fine.lists

import site.budanitskaya.chemistryquiz.fine.R
import site.budanitskaya.chemistryquiz.fine.models.Topic

val topics: MutableList<Topic> = mutableListOf(
    Topic(
        id = 1,
        name = "Acids and bases",
        drawable = R.drawable.acids_and_bases,
        isOpen = true
    ),
    Topic(
        id = 2,
        name = "Biochemistry",
        drawable = R.drawable.biochemistry,
        isOpen = false
    ),

    Topic(
        id = 3,
        name = "Periodic table",
        drawable = R.drawable.periodic_table,
        isOpen = false
    ),
    Topic(
        id = 4,
        name = "Organic chemistry",
        drawable = R.drawable.organic_chemistry,
        isOpen = false
    ),
    Topic(
        id= 5,
        name = "Atomic structure",
        drawable = R.drawable.atomic_structure,
        isOpen = false
    ),
    Topic(
        id = 6,
        name = "Chemical bonds",
        drawable = R.drawable.chemical_bonds,
        isOpen = false
    ),

    Topic(
        id = 7,
        name = "Energy in chemical reactions",
        drawable = R.drawable.energy,
        isOpen = false
    ),
    Topic(
        id = 8,
        name = "Redox reactions",
        drawable = R.drawable.redox,
        isOpen = false
    ),
    Topic(
        id = 9,
        name = "Electrolytes",
        drawable = R.drawable.electrolytes,
        isOpen = false
    ),
    Topic(
        id = 10,
        name = "Crystals",
        drawable = R.drawable.crystals,
        isOpen = false
    )
)