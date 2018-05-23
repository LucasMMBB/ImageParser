import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetImageInfo {

    public static void main(String[] args){
        System.out.println("Main funtion start");
        new GetImageInfo().getInfoFromInternet(1);
    }

    public void getInfoFromInternet(int tar){
        List<String> stringList = new ArrayList<>();
        for(int i=0; i<tar; i+=20){
            String doing = "skip="+i;
            stringList.add(doing);
        }

        for(String list: stringList){
            // parse string
            parseJson(NetUtil.getUtil().getJson(list));
        }
    }

    private void parseJson(String json){
        JSONObject jsonObject = new JSONObject(json);
        String msg = jsonObject.getString("msg");

        if(msg.equals("success")){
            JSONObject jsonObject1 = jsonObject.getJSONObject("res");
            JSONArray jsonArray = jsonObject1.getJSONArray("wallpaper");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                if(!isExits(id)){
                    DownLoader.getDownLoader().startDownload(id);
                    System.out.println("downloading!!!" + id);
                }
            }
        }
    }

    private boolean isExits(String id){
        boolean isExits = false;
        String root = "/home/maoxu/Downloads/tmp";
        File file = new File(root);

        if(file.isDirectory()){
            File[] files1 = file.listFiles();

            if(files1 == null){
                return isExits;
            }

            for(File file1 :  files1){
                String name = file1.getName();
                name = name.substring(0,name.indexOf("."));
                if(name.equals(id) && file1.length() != 0){
                    isExits = true;
                }
            }
        }

        return isExits;
    }

}
