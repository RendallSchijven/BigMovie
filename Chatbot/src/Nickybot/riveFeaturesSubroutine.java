package Nickybot;

import com.rivescript.macro.Subroutine;

import java.util.Calendar;


/**
 *
 * @author Groep 16
 */
public class riveFeaturesSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        String result = "";
        if(args[0].equals("year")){
            result = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        }
        if(args[0].equals("age")){
            result = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1995);
        }
        if(args[0].equals("name")){

            result = Nickybot.getName();
            if(Nickybot.getName().equals("Rendall")){
                result = "Daddy";
            }
        }
        if(args[0].equals("album")){
            result += "I'm gonna trade this life for fortune and fame\n";
            result += "I'd even cut my hair and change my name\n";
            result += "Cause we all just wanna be great " + args[1] + "'s\n";
            result += "And live in hilltop houses driving fifteen " + args[2] + "'s\n";
            switch (args[3]) {
                case "men":
                    result += "The boys come easy and the" + args[4] + "'s come cheap\n";
                    result += "We'll all stay skinny 'cause we just won't eat\n";
                    result += "And we'll hang out in the coolest" + args[5] + "'s\n";
                    result += "In the " + args[5] + "'s with the movie stars\n";
                    result += "Every good gold digger's gonna wind up there\n";
                    result += "Every Chip n' Dale with his bleach blond hair,\n";
                    break;
                case "woman":
                    result += "The girls come easy and the" + args[4] + "'s come cheap\n";
                    result += "We'll all stay skinny 'cause we just won't eat\n";
                    result += "And we'll hang out in the coolest" + args[5] + "'s\n";
                    result += "In the " + args[5] + "'s with the movie stars\n";
                    result += "Every good gold digger's gonna wind up there\n";
                    result += "Every Playboy Bunny with her bleach blond hair,\n";
                    break;
                default:
                    result += "The " + args[3] + "come easy and the" + args[4] + "'s come cheap\n";
                    result += "We'll all stay skinny 'cause we just won't eat\n";
                    result += "And we'll hang out in the coolest" + args[5] + "'s\n";
                    result += "In the " + args[5] + "'s with the movie stars\n";
                    result += "Every good gold digger's gonna wind up there\n";
                    result += "Every" + args[3] + "with its bleach blond hair,\n";
                    break;
            }

            result+= "And wel,\n";
            result+= "Hey, hey, I wanna be a  " + args[1] + "\n";
            result+= "Hey, hey, I wanna be a  " + args[1];

        }
        return result;
    }

}