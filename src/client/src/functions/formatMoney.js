export function formatMoney(numberOfCents) {
    return (numberOfCents / 100).toLocaleString("en-US", {style:"currency", currency:"USD"});
}