/**
 * Created by Administrator on 2014/5/26 0026.
 */
var date = function(date) {
    return new Date(parseInt(date)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ").replace(/上午/g, "AM").replace(/下午/g, "PM");
}
