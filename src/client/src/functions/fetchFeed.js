import {removeHoursFromTimestampStr} from "./timeDifference";

export function fetchFeed(domainName, fetch_type) {
    const requestEndpoint = ''.concat(domainName + "/pay/getLatest",
        fetch_type,
        "Payment",
    );

    const request = new Request(requestEndpoint, {
            method: "GET",
            headers: {"Access-Control-Allow-Origin": "*", "Cache-Control": "no-cache"},
            credentials: 'include'
        }
    );

    return fetch(request);
}

export function fetchFeedFrom(domainName, fetch_type, timestampStr) {

    const adjustedTimestampStr = removeHoursFromTimestampStr(timestampStr, 4);
    console.log(adjustedTimestampStr);
    const requestEndpoint = encodeURI(''.concat(domainName + "/pay/getLatest",
        fetch_type,
        "PaymentFrom",
        "?timestamp=",
        adjustedTimestampStr
    ));

    const request = new Request(requestEndpoint, {
            method: "GET",
            headers: {"Access-Control-Allow-Origin": "*", "Cache-Control": "no-cache"},
            credentials: 'include'
        }
    );

    console.log(request);

    return fetch(request);
}