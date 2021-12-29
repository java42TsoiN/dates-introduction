package telran.application.dates;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {
private static final TextStyle WEEK_DAY_LENGTH = TextStyle.SHORT_STANDALONE;
static DayOfWeek[] daysOfWeek;
private static Locale locale = Locale.forLanguageTag("en");
	public static void main(String[] args) {
		/*********************************************/
		//just for understanding parameter args
		//sun of two numbers defined in the args
//		if(args.length<2) {
//			System.out.println("usage: two numbers for summing");
//			return;
//		}
//		int op1 = Integer.parseInt(args[0]);
//		int op2 = Integer.parseInt(args[1]);
//		System.out.println(op1+op2);
		/*********************************************/
		try {
			
		
		//TODO Part for arguments processing
		//java -jar <jar file name> <month number> <year> <full name of day of week (SUNDAY uppercase)>
		//no arguments - current month, current year, MONDAY
		//no year, no week day - current year, MONDAY
		//no week day - MONDAY
		setDaysOfWeek(args);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		int month = LocalDate.now().getMonthValue();
		int year=LocalDate.now().getYear();
		if(args.length==1) {
			month = getMonth(args[0]);
			System.out.println(year);
		}
		if(args.length>=2){
			month = getMonth(args[0]);
			year = getYear(args[1]);
		}
		PrintCalendar(month,year);
		
	}

	private static void setDaysOfWeek(String[] args) throws Exception{
		if(args.length>2) {
			int startDay =setDay(args[2]);
			if(startDay==1) {
				daysOfWeek =  new DayOfWeek[] {DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY,DayOfWeek.SATURDAY,DayOfWeek.SUNDAY};
			}
		}else {
//			daysOfWeek = DayOfWeek.values();
			daysOfWeek = new DayOfWeek[] {DayOfWeek.SUNDAY,DayOfWeek.MONDAY,DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY,DayOfWeek.THURSDAY,DayOfWeek.FRIDAY,DayOfWeek.SATURDAY};
		}
		
	}

	private static int setDay(String day) throws NumberFormatException{
		return DayOfWeek.valueOf(day.toUpperCase()).getValue();		
	}

	private static int getYear(String year) throws NumberFormatException {
		return Integer.parseInt(year);
	}

	private static int getMonth(String month) throws IllegalArgumentException{
		return Integer.parseInt(month);
	}

	private static void PrintCalendar(int month, int year) {
		printTitle(month, year);
		printWeekDays();
		printDates(month, year);
	}

	private static void printDates(int month, int year) {
		int firstColumn = getFirstColumn(month, year);
		printOffSet(firstColumn);
		int days = getDaysNumber(month, year);
		int columnWidth  = getColumnWidth();
		int line = 1;
		for(int i = 1; i<=days;i++) {
			System.out.printf("%"+ columnWidth+"d",i);
			if((line+firstColumn)%daysOfWeek.length==0) {
				System.out.println();
				firstColumn=0;
			}else {
				firstColumn++;
			}
		}
	}

	private static int getColumnWidth() {
		
		return (daysOfWeek[0].getDisplayName(WEEK_DAY_LENGTH, locale)+" ").length();
	}

	private static int getDaysNumber(int month, int year) {
		return YearMonth.of(year, month).lengthOfMonth();
	}

	private static void printOffSet(int firstColumn) {
		System.out.print(" ".repeat(firstColumn*getColumnWidth()));		
	}

	private static int getFirstColumn(int month, int year) {
		LocalDate firstDateMonth=LocalDate.of(year, month, 1);
		int firstWeekDay = firstDateMonth.getDayOfWeek().getValue();
		int firstValue = daysOfWeek[0].getValue();
		int delta = firstWeekDay - firstValue;
		return delta>=0?delta:delta+daysOfWeek.length;
	}

	private static void printWeekDays() {
		String res = " ".repeat(getColumnWidth()/2);
		for (int i =0; i< daysOfWeek.length;i++) {
			res+=daysOfWeek[i].getDisplayName(WEEK_DAY_LENGTH, locale)+ " ";
		}
		System.out.println(res);
	}

	private static void printTitle(int month, int year) {
		Month monthObj = Month.of(month);
		System.out.printf("%s, %d\n",monthObj.getDisplayName(TextStyle.FULL_STANDALONE, locale),year);
	}

}
