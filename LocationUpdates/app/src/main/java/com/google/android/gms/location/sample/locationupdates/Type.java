package com.google.android.gms.location.sample.locationupdates;

public enum Type
{
    raccoon, penguin, panda, bull, elephant, gorilla, koala, polarbear, rhinoceros, sloth;

    public static Type randomType()
    {
        int rand = (int)(Math.random()*10 + 1);
        Type type;

        switch (rand)
        {
            case 1:
                type = Type.raccoon;
                break;
            case 2:
                type = Type.panda;
                break;
            case 3:
                type = Type.penguin;
                break;
            case 4:
                type = Type.bull;
                break;
            case 5:
                type = Type.elephant;
                break;
            case 6:
                type = Type.gorilla;
                break;
            case 7:
                type = Type.koala;
                break;
            case 8:
                type = Type.polarbear;
                break;
            case 9:
                type = Type.rhinoceros;
                break;
            case 10:
                type = Type.sloth;
                break;
            default:
                type = Type.raccoon;
                break;

        }

        return type;
    }

    public static int getDrawableIDByType(Type type)
    {
        int drawableID;

        switch(type)
        {
            case raccoon:
                drawableID = R.drawable.raccoon;
                break;
            case panda:
                drawableID = R.drawable.panda;
                break;
            case penguin:
                drawableID = R.drawable.penguin;
                break;
            case bull:
                drawableID = R.drawable.bull;
                break;
            case elephant:
                drawableID = R.drawable.elephant;
                break;
            case gorilla:
                drawableID = R.drawable.gorilla;
                break;
            case koala:
                drawableID = R.drawable.koala;
                break;
            case polarbear:
                drawableID = R.drawable.polarbear;
                break;
            case rhinoceros:
                drawableID = R.drawable.rhinoceros;
                break;
            case sloth:
                drawableID = R.drawable.sloth;
                break;
            default:
                drawableID = R.drawable.raccoon;
                break;
        }

        return drawableID;
    }
}


