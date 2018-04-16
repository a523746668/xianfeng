package com.qingyii.hxtz.util;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtil {
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    private static final String TIME = "HH:mm:dd";
    private static final String YEAR = "yyyy";
    private static final String MONTH = "MM";
    private static final String DAY = "dd";
    private static final String HOUR = "HH";
    private static final String MINUTE = "mm";
    private static final String SEC = "ss";
    public static final String DATETIMECHINESE = "yyyy��MM��dd�� HHʱmm��ss��";
    public static final String DATECHINESE = "yyyy��MM��dd��";
    private static final String SIMPLEDATECHINESE = "MM��dd��";
    /**
     * ��Ϣ�����б����ʱ���ʽ
     */
    private static final String msgfm01 = "MM-dd HH:mm";
    /**
     * ��Ϣ�����б����ǰʱ���ʽ
     */
    private static final String msgfm02 = "yyyy-MM-dd HH:mm";

    /**
     * ����ʱ�������ָ����ʽ�ַ�
     *
     * @param timeLong
     * @param formats
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String TimeStamp2Date(Long timeLong, String formats) {
        Long timestamp = timeLong * 1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }

    /**
     * �ж�һ���ַ������Ƿ����
     *
     * @param dateTime
     * @return (int)&nbsp;���ڷ���1�������ڷ���0
     * @throws ParseException
     */
    public static int isOutOfDate(String dateTime) throws ParseException {
        long nowTimeLong = new Date().getTime();
        long ckTimeLong = new SimpleDateFormat(DATETIME).parse(dateTime)
                .getTime();
        if (nowTimeLong - ckTimeLong > 0) {// ����
            return 1;
        }
        return 0;
    }

    /**
     * �ж��Ƿ���һ����ֹ������<br/>
     * ����:2012-04-05 00:00:00~2012-04-15 00:00:00
     *
     * @param start_time
     * @param over_time
     * @return (int)&nbsp;�����ʱ����ڷ���1�����ڷ���0
     * @throws ParseException
     */
    public static int isOutOfDate(String start_time, String over_time)
            throws ParseException {
        long nowTimeLong = new Date().getTime();
        long ckStartTimeLong = new SimpleDateFormat(DATETIME).parse(start_time)
                .getTime();
        long ckOverTimeLong = new SimpleDateFormat(DATETIME).parse(over_time)
                .getTime();
        if (nowTimeLong > ckStartTimeLong && nowTimeLong < ckOverTimeLong) {
            return 1;
        }
        return 0;
    }

    /**
     * �ж�һ���Զ��������Ƿ���һ����ֹ������<br/>
     * ����:�ж�2012-01-05 00:00:00�Ƿ���2012-04-05 00:00:00~2012-04-15 00:00:00
     *
     * @param start_time
     * @param over_time
     * @return (int)&nbsp;�����ʱ����ڷ���1�����ڷ���0
     * @throws ParseException
     */
    public static int isOutOfDate(String time, String start_time,
                                  String over_time) throws ParseException {
        long timeLong = new SimpleDateFormat(DATETIME).parse(time).getTime();
        long ckStartTimeLong = new SimpleDateFormat(DATETIME).parse(start_time)
                .getTime();
        long ckOverTimeLong = new SimpleDateFormat(DATETIME).parse(over_time)
                .getTime();
        if (timeLong > ckStartTimeLong && timeLong < ckOverTimeLong) {
            return 1;
        }
        return 0;
    }

    /**
     * �ж��Ƿ���һ��ʱ�����<br/>
     * ����:8:00~10:00
     *
     * @param time_limit_start
     * @param time_limit_over
     * @return (int) 1�����ʱ����ڣ�0�������ʱ�����
     * @throws ParseException
     */
    public static int isInTime(String time_limit_start, String time_limit_over)
            throws ParseException {
        // ��ȡ��ǰ����
        String nowDate = new SimpleDateFormat(DATE).format(new Date());
        return isOutOfDate(nowDate + " " + time_limit_start, nowDate + " "
                + time_limit_over);
    }

    /**
     * �ж�һ���Զ���ʱ���Ƿ���һ��ʱ�����<br/>
     * ����:�ж�02:00�Ƿ���08:00~10:00ʱ�����
     *
     * @param time_limit_start
     * @param time_limit_over
     * @return (int) 1�����ʱ����ڣ�0�������ʱ�����
     * @throws ParseException
     */
    public static int isInTime(String time, String time_limit_start,
                               String time_limit_over) throws ParseException {
        String nowDate = new SimpleDateFormat(DATE).format(new Date());
        return isOutOfDate(nowDate + " " + time, nowDate + " "
                + time_limit_start, nowDate + " " + time_limit_over);
    }

    /**
     * ȡ���Զ����·ݺ�����ڣ���13�����Ժ��ʱ��
     *
     * @param monthNum ��󼸸���
     * @return ʱ���ַ�
     */
    public static String crateTimeFromNowTimeByMonth(int monthNum) {
        Calendar calendar = new GregorianCalendar(Integer.parseInt(getYear()),
                Integer.parseInt(getMonth()) - 1, Integer.parseInt(getDay()),
                Integer.parseInt(getHour()), Integer.parseInt(getMinute()),
                Integer.parseInt(getSec()));
        calendar.add(Calendar.MONTH, monthNum);
        return new SimpleDateFormat(DATETIME).format(calendar.getTime());
    }

    /**
     * ȡ���Զ������������ڣ���13���Ժ��ʱ��
     *
     * @param dayNum �����
     * @return ʱ���ַ�(DateTime)
     */
    public static String crateTimeFromNowTimeByDay(int dayNum) {
        Calendar calendar = new GregorianCalendar(Integer.parseInt(getYear()),
                Integer.parseInt(getMonth()) - 1, Integer.parseInt(getDay()),
                Integer.parseInt(getHour()), Integer.parseInt(getMinute()),
                Integer.parseInt(getSec()));
        calendar.add(Calendar.DATE, dayNum);
        return new SimpleDateFormat(DATETIME).format(calendar.getTime());
    }

    /**
     * ȡ���Զ������������ڣ���13���Ժ��ʱ��
     *
     * @param dayNum �����
     * @return ʱ���ַ�(Date)
     */
    public static String crateTimeFromNowDayByDay(int dayNum) {
        Calendar calendar = new GregorianCalendar(Integer.parseInt(getYear()),
                Integer.parseInt(getMonth()) - 1, Integer.parseInt(getDay()),
                Integer.parseInt(getHour()), Integer.parseInt(getMinute()),
                Integer.parseInt(getSec()));
        calendar.add(Calendar.DATE, dayNum);
        return new SimpleDateFormat(DATE).format(calendar.getTime());
    }

    /**
     * ȡ���Զ���ʱ����ٹ���ӵ�ʱ�䣬��12:05�Ժ�5���ӵ�ʱ��
     *
     * @param dayNum �����
     * @return ʱ���ַ�(Date)
     */
    public static String crateTimeFromNowDayByTime(int timeNum) {
        Calendar calendar = new GregorianCalendar(Integer.parseInt(getYear()),
                Integer.parseInt(getMonth()) - 1, Integer.parseInt(getDay()),
                Integer.parseInt(getHour()), Integer.parseInt(getMinute()),
                Integer.parseInt(getSec()));
        calendar.add(Calendar.MINUTE, timeNum);
        return new SimpleDateFormat(DATETIME).format(calendar.getTime());
    }

    /**
     * ��������ʱ����(��ȷ������)
     *
     * @param startDay  ��ʼ��(����):0��ʾ���գ�1��ʾ����
     * @param startTime ��ʼʱ��(24h):00:00
     * @param endDay    ������(����):0��ʾ���գ�1��ʾ���գ����ƣ����ڵ��� startDay
     * @param endTime   ����ʱ��(24h):23:50
     * @return ��ʽ�������ڸ�ʽ��DD��HHСʱmm��
     */
    public static String calculateIntervalTime(int startDay, String startTime,
                                               int endDay, String endTime) {
        int day = endDay - startDay;
        int hour = 0;
        int mm = 0;
        if (day < 0) {
            return null;
        } else {
            int sh = Integer.valueOf(startTime.split(":")[0]);
            int eh = Integer.valueOf(endTime.split(":")[0]);
            int sm = Integer.valueOf(startTime.split(":")[1]);
            int em = Integer.valueOf(endTime.split(":")[1]);
            hour = eh - sh;
            if (hour > 0) {
                mm = em - sm;
                if (mm < 0) {
                    hour--;
                    mm = 60 + mm;
                }
            } else {
                day = day - 1;
                hour = 24 + hour;
                mm = em - sm;
                if (mm < 0) {
                    hour--;
                    mm = 60 + mm;
                }
            }
        }
        if (hour == 24) {
            day++;
            hour = 0;
        }
        if (day != 0) {
            return day + "��" + hour + "Сʱ" + mm + "��";
        } else {
            return hour + "Сʱ" + mm + "��";
        }
    }

    /**
     * ��������ʱ���
     *
     * @param startTime
     * @param endTime
     * @return long
     * @throws ParseException
     */
    public static long calculateIntervalTime(String startTime, String endTime)
            throws ParseException {
        return parseDateTime(endTime).getTime()
                - parseDateTime(startTime).getTime();
    }

    /**
     * ��������ʱ���02
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static long calculateIntervalTime02(Timestamp startTime,
                                               Timestamp endTime) throws ParseException {
        return endTime.getTime() - startTime.getTime();
    }

    // �ַ�ת����ʱ��
    public static Date parseDateTime(String datetime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
        return sdf.parse(datetime);
    }

    // ��ȡ��ǰ��ϸ����ʱ��
    public static String getDateTime() {
        return new SimpleDateFormat(DATETIME).format(new Date());
    }

    // ת��Ϊ����ʱ��
    public static String getChineseDateTime() {
        return new SimpleDateFormat(DATETIMECHINESE).format(new Date());
    }

    // ת��Ϊ����ʱ��
    public static String getChineseDate() {
        return new SimpleDateFormat(DATECHINESE).format(new Date());
    }

    // ת��Ϊ����ʱ��
    public static String getSimpleChineseDate() {
        return new SimpleDateFormat(SIMPLEDATECHINESE).format(new Date());
    }

    // ת��Ϊ����ʱ�� ���numΪ-1��ʾǰһ�� 1Ϊ��һ�� 0Ϊ����
    public static String getSimpleChineseDate(int num) {
        Date d = new Date();
        try {
            d = parseDateTime(crateTimeFromNowTimeByDay(num));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(SIMPLEDATECHINESE).format(d);
    }

    // ��ȡ��ǰʱ��
    public static String getTime() {
        return new SimpleDateFormat(TIME).format(new Date());
    }

    // ��ȡ��ǰ��
    public static String getYear() {
        return new SimpleDateFormat(YEAR).format(new Date());
    }

    // ��ȡ��ǰ��
    public static String getMonth() {
        return new SimpleDateFormat(MONTH).format(new Date());
    }

    // ��ȡ��ǰ��
    public static String getDay() {
        return new SimpleDateFormat(DAY).format(new Date());
    }

    // ��ȡ��ǰʱ
    public static String getHour() {
        return new SimpleDateFormat(HOUR).format(new Date());
    }

    // ��ȡ��ǰ��
    public static String getMinute() {
        return new SimpleDateFormat(MINUTE).format(new Date());
    }

    // ��ȡ��ǰ��
    public static String getSec() {
        return new SimpleDateFormat(SEC).format(new Date());
    }

    // ��ȡ��������
    public static String getYestday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        return new SimpleDateFormat(DATETIME).format(d);// ��ȡ��������
    }

    public static String getMonday() {
        Calendar calendar = new GregorianCalendar();
        // ȡ�ñ���һ
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat(DATETIME).format(calendar.getTime());// ��ȡ��������
    }

    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis();
        long timeGap = (currentSeconds - timestamp) / 1000;// ������ʱ���������
        String timeStr = "";
        if (timeGap > 24 * 60 * 60) {// 1������
            timeStr = timeGap / (24 * 60 * 60) + "��ǰ";
        } else if (timeGap > 60 * 60) {// 1Сʱ-24Сʱ
            timeStr = timeGap / (60 * 60) + "Сʱǰ";
        } else if (timeGap > 60) {// 1����-59����
            timeStr = timeGap / 60 + "����ǰ";
        } else {// 1����-59����
            timeStr = "�ո�";
        }
        return timeStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getLongBystr(String dateStr, String dateFormate) {
        long millionSeconds = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
            millionSeconds = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return millionSeconds;
    }

    /**
     * ��ȡ����ʱ��ָ���ַ��ʽ
     *
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayTime(String df) {
        String dayTimeStr = "";
        // "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        Date date = new Date();
        dayTimeStr = sdf.format(date);
        return dayTimeStr;
    }

    /**
     * ��ָ��ʱ�䵽��ǰʱ���
     *
     * @param timeStr
     * @return
     */
    public static String sjc(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        // ����ȡ����ʱ���ַ�ת��Ϊʱ���ʽ���ַ�
        Date endTime = null;
        try {
            // beginTime = sdf.parse("2010-07-27 12:53:30");
            endTime = sdf.parse(timeStr + " 23:59:59");
            // Ĭ��Ϊ���룬����1000��Ϊ��ת������
            long interval = (endTime.getTime() - currentTime.getTime()) / 1000;// ��
            // long interval=(currentTime.getTime()-timeStr)/1000;//��
            long day = interval / (24 * 3600);// ��
            long hour = interval % (24 * 3600) / 3600;// Сʱ
            long minute = interval % 3600 / 60;// ����
            long second = interval % 60;// ��
            // System.out.println("����ʱ����"+day+"��"+hour+"Сʱ"+minute+"��"+second+"��");
            return day + "��" + hour + "Сʱ" + minute + "��" + second + "��";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ��ȡ����
     *
     * @param timeStr
     * @return
     */
    public static String getOldYear(long birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //�õ���ǰ�����
        String cYear = sdf.format(new Date()).substring(0, 4);
        String birthDay = TimeStamp2Date(birthday, DATE).substring(0, 4);
        //�õ��������
        String birthYear = birthDay.substring(0, 4);
        int age = Integer.parseInt(cYear) - Integer.parseInt(birthYear) + 1;
        return age + "";
    }

    /**
     * ������1993-02-12������
     */
    public static int getOldByBirthday(String birthday) {
        int old = 0;
        String[] a = birthday.split("-");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);//�õ���
        old = year - Integer.parseInt(a[0]) + 1;
        return old;
    }

    /**
     * ��ʱ���ת����yyyy-MM-dd��ʽ
     *
     * @param cc_time ʱ���
     * @return
     */
    public static String getStrTime3(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // ���磺cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));

        return re_StrTime;

    }

    /**
     * �õ���ǰ������ָ������������� yyyy-MM-dd
     *
     * @param date ָ������ yyyy-MM-dd��ʽ
     */
    public static int getBetweenDays(String date) {
        String currentdate = getDayTime("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date cDate = null, dDate = null;
        try {
            cDate = df.parse(currentdate);
            dDate = df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int betweenDate = (int) ((cDate.getTime() - dDate.getTime()) / (24 * 60 * 60 * 1000));
        return betweenDate;
    }

    /**
     * �õ�ָ���������n��֮������� �����ַ���ʽ
     *
     * @param date ָ������ ʱ�����ʽ
     * @param n    �������
     */
    public static String getDateAfterNDay(String date, int n) {
        long lcc_time = Long.valueOf(date);
        Date date1 = new Date(lcc_time * 1000L);
        Date newDate = new Date();
        Calendar calendar = Calendar.getInstance(); // �õ�����
        calendar.setTime(date1);// �ѵ�ǰʱ�丳������
        calendar.add(Calendar.DAY_OF_MONTH, n); // ����Ϊ��n��
        newDate = calendar.getTime(); // �õ���n���ʱ��

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ����ʱ���ʽ
        String newDateStr = sdf.format(newDate); // ��ʽ��ǰһ��
        return newDateStr;
    }

    /**
     * �õ�ָ���������n��֮������� ����date������ʽ
     *
     * @param date ָ������ ʱ�����ʽ
     * @param n    �������
     */
    public static Date getDateAfterNDay1(String date, int n) {
        long lcc_time = Long.valueOf(date);
        Date date1 = new Date(lcc_time * 1000L);
        Date newDate = new Date();
        Calendar calendar = Calendar.getInstance(); // �õ�����
        calendar.setTime(date1);// �ѵ�ǰʱ�丳������
        calendar.add(Calendar.DAY_OF_MONTH, n); // ����Ϊ��n��
        newDate = calendar.getTime(); // �õ���n���ʱ��

        return newDate;
    }

    /**
     * ���ַ�2014-01-25ת��2014��01��25��
     *
     * @param oldstr Ҫת�����ַ�
     */
    public static String changeDateStr(String oldstr) {
        String newStr = "";
        String[] a = oldstr.split("-");
        newStr = a[0] + "��" + a[1] + "��" + a[2] + "��";
        return newStr;
    }

    /**
     * �ж���ָ��ʱ���Ƿ����ڽ���
     *
     * @param myString
     * @return
     */
    public static boolean checkTime(String dataStr, String DfStr) {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DfStr, Locale.CHINA);
        Date d = null;
        try {
            d = sdf.parse(dataStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean flag = d.before(nowdate);
        if (flag) {
            // System.out.print("���ڽ���") ;
            return true;
        } else {
            // System.out.print("���ڽ���") ;
            return false;
        }
    }

    /**
     * �ҵ���Ϣ--���б�ʱ���ʽ
     *
     * @return
     */
    public static String msgListDateFM(String time) {
        String timeStr = "";
        String formTimeStr = TimeStamp2Date(Long.parseLong(time), DATE);
        String from[] = formTimeStr.split("-");
        String nowTimeStr = new SimpleDateFormat(DATE).format(new Date());
        String nowYear = getYear();
        if (formTimeStr.equals(nowTimeStr)) {
            //����ǵ���ʱ��
            timeStr = TimeStamp2Date(Long.parseLong(time), "HH:mm");
        } else {
            if (nowYear.equals(from[0])) {
                //����
                timeStr = TimeStamp2Date(Long.parseLong(time), msgfm01);
            } else {
                //����ǰ
                timeStr = TimeStamp2Date(Long.parseLong(time), msgfm02);
            }
        }
        return timeStr;
    }

    /**
     * 整数(秒数)转换为时分秒格式(xx:xx:xx)
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 整数(秒数)转换为时分秒格式(xx时xx分xx秒)
     *
     * @param time
     * @return
     */
    public static String secToTime02(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00分00秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                if (minute <= 0) {
                    timeStr = unitFormat(second) + "秒";
                } else {
                    timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
                }
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99时59分59秒";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    public static String getRecentTimeDisplayOfUnixTimestamp(long timestamp) {
        long now = System.currentTimeMillis();
        long duration = (now - timestamp) / 1000;
        if (duration <= 60) return "刚刚";
        else if (duration <= 3600) return (duration / 60) + "分钟前";
        else if (duration < 86400) return (duration / 3600) + "小时前";
        else return getDateTimeOfUnixTimestamp(timestamp / 1000);
    }

    public static String getDateTimeOfUnixTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp * 1000L));
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}