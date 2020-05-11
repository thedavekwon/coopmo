function formatSingularOrPlural(timeDiff, units) {
    const timeDiffFloor = Math.floor(timeDiff);
    if (timeDiffFloor === 1) {
        return timeDiffFloor.toString() + " " + units + " ago";
    } else {
        return timeDiffFloor.toString() + " " + units + "s ago";
    }
}

export function getTimeAgoStr(timestamp) {
    const currentTimestamp = new Date();
    const millisecPerSec = 1000;
    const secPerMin = 60;
    const minPerHour = 60;
    const hourPerDay = 24;

    const milliSecondDiff = Math.abs(currentTimestamp.getTime() - timestamp.getTime());
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