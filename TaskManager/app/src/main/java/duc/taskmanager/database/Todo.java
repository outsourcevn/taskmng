package duc.taskmanager.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import duc.taskmanager.util.Constract;

/**
 * Created by Duc on 5/12/2016.
 * Note: when update database must update AA_DB_VERSION
 */
    @Table(name = "tbTodo")
public class Todo extends Model {
    public static final String PRIORITY = Constract.PRIORITY;
    public static final String COMPER = Constract.COMPLETEPERCENT;
    public static final String START = Constract.START;
    public static final String END = Constract.END;
    public static final String TYPE = Constract.TYPE;
    public static final String NAME = Constract.NAME;
    public static final String DETAIL = Constract.DETAIL;
    public static final String MEDIA = Constract.MEDIA;

    @Column(name = PRIORITY)
    private String priority;

    @Column(name = COMPER)
    private String comPer;

    @Column(name = START)
    private String start;

    @Column(name = END)
    private String end;

    @Column(name = TYPE)
    private String type;

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

    public String getType() {
        return priority;
    }

    public Todo setType(String priority) {
        this.priority = priority;
        return this;
    }

    public String getName() {
        return priority;
    }

    public Todo setName(String priority) {
        this.priority = priority;
        return this;
    }

    public String getDetail() {
        return priority;
    }

    public Todo setDetail(String priority) {
        this.priority = priority;
        return this;
    }

    public String getMedia() {
        return priority;
    }

    public Todo setMedia(String priority) {
        this.priority = priority;
        return this;
    }
}
