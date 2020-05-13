export function likeTransaction(domainName, transactionId, transactionType) {
    const requestEndpoint = ''.concat(domainName, "/transaction/likeTransaction");
    const request = new Request(requestEndpoint, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
                "Content-Type": "application/json"},
            credentials: 'include',
            body: JSON.stringify({
                transactionId: transactionId,
                transactionType: transactionType
            })
        }
    );
    return fetch(request);
}