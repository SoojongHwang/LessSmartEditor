package com.example.kepler.lesssmarteditor.editor.model.component;

import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.map.model.Item;

/**
 * Created by Kepler on 2017-05-25.
 */

public class ComponentManager {
    private String baseUrl = "https://openapi.naver.com/v1/map/staticmap.bin?clientId=hOBAjjmz9dUkwoGrp6pS&url=http://naver.com&crs=NHN:128&level=11&w=600&h=400&&baselayer=default&level=11&";

    public TextComponent getTextInstance() {
        return new TextComponent(Type.TEXT, "");
    }

    public ImageComponent getImageInstance(Uri uri) {
        return new ImageComponent(Type.IMAGE, uri);
    }

    public MapComponent getMapInstance(Item item) {
        String title = getRefinedTitle(item.title);
        String url = getUrl(item.mapx, item.mapy);
        return new MapComponent(Type.MAP, title, item.roadAddress, url);
    }

    private String getUrl(float x, float y) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&center=" + (int) x + "," + (int) y)
                .append("&markers=" + (int) x + "," + (int) y);
        return builder.toString();
    }
    private String getRefinedTitle(String str) {
        str = str.replace("<b>", " ");
        return str.replace("</b>", " ");
    }
}
