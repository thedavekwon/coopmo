export function fetchFeed(domainName, num_latest, fetch_type) {
    const requestEndpoint = ''.concat(domainName + "/pay/getLatest",
        fetch_type,
        "Payment",
        "?n=",
        num_latest.toString()
    );
    console.log(requestEndpoint);
    const request = new Request(requestEndpoint, {
            method: "GET",
            headers: {"Access-Control-Allow-Origin": "*", "Cache-Control": "no-cache"},
            credentials: 'include'
        }
    );

    return fetch(request);
}