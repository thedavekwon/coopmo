import {getTimeObjFromTimestamp} from "./timeDifference";

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
    // const timestamp = getTimeObjFromTimestamp(timestampStr);
    // const timestampNanos = timestamp.getTime()*1000;
    const requestEndpoint = encodeURI(''.concat(domainName + "/pay/getLatest",
        fetch_type,
        "PaymentFrom",
        "?timestamp=",
        timestampStr
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