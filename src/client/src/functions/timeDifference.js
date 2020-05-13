function getTimeObjFromTimestamp(timestampStr) {
    const year = parseInt(timestampStr.substring(0, 4));
    const month = parseInt(timestampStr.substring(5,7));
    const day = parseInt(timestampStr.substring(8,10));
    const hour = parseInt(timestampStr.substring(11, 13));
    const minute = timestampStr.substring(14, 16);
    const second = timestampStr.substring(17, 19);
    const millisecond = timestampStr.substring(19, 23);

    const timestamp = new Date(year, month-1, day, hour, minute, second, millisecond);
    const hourAdjustment = 4;
    const timezoneAdjustedTimestamp = new Date(timestamp - hourAdjustment*60*60*1000);
    return timezoneAdjustedTimestamp;
}

function formatSingularOrPlural(timeDiff, units) {
    const timeDiffFloor = Math.floor(timeDiff);
    if (timeDiffFloor === 1) {
        return timeDiffFloor.toString() + " " + units + " ago";
    } else {
        return timeDiffFloor.toString() + " " + units + "s ago";
    }
}

export function getTimeAgoStr(timestampStr) {
    const timestamp = getTimeObjFromTimestamp(timestampStr);
    const currentTimestamp = new Date();
    const millisecPerSec = 1000;
    const secPerMin = 60;
    const minPerHour = 60;
    const hourPerDay = 24;

    const milliSecondDiff = currentTimestamp.getTime() - timestamp.getTime();
    const secDiff = milliSecondDiff/millisecPerSec;
    const minDiff = secDiff/secPerMin;
    const hourDiff = minDiff/minPerHour;
    const dayDiff = hourDiff/hourPerDay;

    if (dayDiff > 1) {
        return formatSingularOrPlural(dayDiff, 'day');
    } else if (hourDiff > 1) {
        return formatSingularOrPlural(hourDiff, 'hour');
    } else if (minDiff > 1) {
        return formatSingularOrPlural(minDiff, 'minute');
    } else if (secDiff > 30) {
        return formatSingularOrPlural(secDiff, 'second')
    } else {
        return "just now";
    }
}