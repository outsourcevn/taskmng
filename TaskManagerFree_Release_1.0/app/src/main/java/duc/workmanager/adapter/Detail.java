package duc.workmanager.adapter;

/**
 * Created by Duc on 5/17/2016.
 */
public class Detail {
    private String tille;
    private String content;
    private String check;

    public String getDetailTittle() {
        return tille;
    }

    public void setDetailTittle(String tille) {
        this.tille = tille;
    }

    public String getDetailContent() {
        return content;
    }

    public void setDetailContent(String content) {
        this.content = content;
    }

    public String getDetailCheckBox() {
        return check;
    }

    public void setDetailCheckBox(String check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return this.tille + "-" + this.content;
    }
}
