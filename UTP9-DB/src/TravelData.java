import java.io.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TravelData {


    private List<Offer> list;



    public TravelData(File dir){

        Stream<Path> stream;


        try {
            stream=Files.walk(dir.toPath());
            this.list=stream
                    .filter(Files::isRegularFile)
                    .flatMap(p -> {
                        try {
                            return Files.lines(p);
                        }catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .map(o -> {
                        if(o==null) {
                            return null;
                        }

                        String[] splt = o.split("\t");
                        try {
                            return new Offer(
                                    splt[0],splt[1],splt[2],splt[3],splt[4],splt[5],splt[6]
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }

                    })
                    .filter(o -> o != null)
                    .collect(Collectors.toList());

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public List<Offer> getList(){
        return this.list;
    }


    public List<String> getOffersDescriptionsList(String loc, String dateFormat){

        Locale locale = Locale.forLanguageTag(loc.replace('_','-'));

        return this.list
                .stream()
                .map(o -> o.localize(locale))
                .collect(Collectors.toList());

    }
}
