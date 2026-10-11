import "../../styles/tables/MonthlyTable.css";

function cleanText(text) {
    if (!text) return "-";
    const trimmed = 
    text.toString()
    .trim();
    return trimmed.length === 0 ? "-" : 
    trimmed;
}

function formatMoney(value) {
    if (value === null || 
    value === undefined || 
    value === "") 
    return "-";

    return `$${Number(value)
    .toFixed(2)}`;
}

export default function MonthlyTable ({expenses = []}) {
    /*
    Sort directly before mapping
    down to rows
    */
    const sortedMonthlyExpenses = 
    [...expenses].sort((a, b) => {
        const monthA = a.month || "";
        const monthB = b.month || "";
        return monthB
        .localeCompare(monthA); 
    });

    return (
        <div className = "monthly-table-wrapper">
            <table className = "monthly-table">
                <thead>
                    <tr>
                        <th>Month</th>
                        <th>Income</th>
                        <th>Emergency</th>
                        <th>Srs</th>
                        <th>Ssb</th>
                        <th>Debt</th>
                        <th>Insurances</th>
                        <th>Bills</th>
                        <th>Tax</th>
                        <th>Parent's Allowances</th>
                        <th>Transport</th>
                        <th>Food</th>
                        <th>Groceries</th>
                        <th>Haircut</th>
                        <th>Household</th>
                        <th>Medical</th>
                        <th>Wants</th>
                        <th>Others</th>
                        <th>Tithes</th>
                        <th>Overspent</th>
                        <th>Available</th>
                    </tr>
                </thead>

                <tbody>
                    {sortedMonthlyExpenses.map((monthly) => (
                        <tr key = {monthly.month}>
                            <td>{cleanText(monthly.month)}</td>
                            <td>{formatMoney(monthly.income)}</td>
                            <td>{formatMoney(monthly.emergency)}</td>
                            <td>{formatMoney(monthly.srs)}</td>
                            <td>{formatMoney(monthly.ssb)}</td>
                            <td>{formatMoney(monthly.debt)}</td>
                            <td>{formatMoney(monthly.insurances)}</td>
                            <td>{formatMoney(monthly.bills)}</td>
                            <td>{formatMoney(monthly.tax)}</td>
                            <td>{formatMoney(monthly.parentsAllowances)}</td>
                            <td>{formatMoney(monthly.transport)}</td>
                            <td>{formatMoney(monthly.food)}</td>
                            <td>{formatMoney(monthly.groceries)}</td>
                            <td>{formatMoney(monthly.haircut)}</td>
                            <td>{formatMoney(monthly.household)}</td>
                            <td>{formatMoney(monthly.medical)}</td>
                            <td>{formatMoney(monthly.wants)}</td>
                            <td>{formatMoney(monthly.others)}</td>
                            <td>{formatMoney(monthly.tithes)}</td>
                            <td>{formatMoney(monthly.overspent)}</td>
                            <td>{formatMoney(monthly.available)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}