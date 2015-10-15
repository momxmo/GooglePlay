package mo.com.googleplay.manager;/**
 * Created by  on
 */

/**
 * @创建者 MoMxMo
 * @创时间 2015/10/15:19:19
 * @描述 TODO
 * @项目名 GooglePlay
 *
 * @版本 $Rev
 * @更新者 $Author
 * @更新时间 $Date
 * @更新描述 TODO
 */
public class DownLoadInfo  {
    public String	downloadUrl;									// 下载地址
    public String	savePath;										// 下载保存的路劲
    public int		curState	= DownLoadManager.STATE_UNDOWNLOAD; // 记录当前的状态
    public String	packageName;									// 应用的包名

    public long		max;											// 进度的最大值
    public long		progress;										// 进度的当前值
}
