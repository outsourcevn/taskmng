package duc.taskmanager.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import duc.taskmanager.util.Constant;

/**
 * Created by Duc on 5/12/2016.
 * Note: when update database must update AA_DB_VERSION
 */
            @Table(name = "tbTodo")
public class Todo extends Model {
    public static final String PRIORITY = Constant.PRIORITY;
    public static final String COMPER = Constant.COMPLETEPERCENT;
    public static final String START = Constant.START;
    public static final String END = Constant.END;
    public static final String TOPIC = Constant.TOPIC;
    public static final String NAME = Constant.NAME;
    public static final String DETAIL = Constant.DETAIL;
    public static final String MEDIA = Constant.MEDIA;

    @Column(name = PRIORITY)
    private String priority;

    @Column(name = COMPER)
    private String comPer;

    @Column(name = START)
    private String start;

    @Column(name = END)
    private String end;

    @Column(name = TOPIC)
    private String topic;

    @Column(name = NAME)
    private String name;

    @Column(name = DETAIL)
    private String detail;

    @Column(name = MEDIA)
    private String media;

    public String getPriority() {
        return priority;
    }

    public Todo setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public String getComper() {
        return comPer;
    }

    public Todo setComper(String comPer) {
        this.comPer = comPer;
        return this;
    }

    public String getStart() {
        return start;
    }

    public Todo setStart(String start) {
        this.start = start;
        return this;
    }

    public String getEnd() {
        return end;
    }

    public Todo setEnd(String end) {
        this.end = end;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public Todo setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getName() {
        return name;
    }

    public Todo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Todo setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getMedia() {
        return media;
    }

    public Todo setMedia(String media) {
        this.media = media;
        return this;
    }
}
