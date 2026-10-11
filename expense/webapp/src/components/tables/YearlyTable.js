import "../../styles/tables/YearlyTable.css";
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

export default function YearlyTable ({expenses = []}) {
    /*Sort years in 
    descending order 
    automatically*/
    const sortedYearlyExpenses = 
    [...expenses]
    .sort((a, b) => {
        const yearA = 
        a.year ? a.year
        .toString() : "";
        const yearB = 
        b.year ? b.year
        .toString() : "";
        return yearB
        .localeCompare(yearA);
    });

    return (
        <div className = "yearly-table-wrapper">
            <table className = "yearly-table">
                <thead>
                    <tr>
                        <th>Year</th>
                        <th>Income</th>
                        <th>Savings</th>
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
                    </tr>
                </thead>

                <tbody>
                    {sortedYearlyExpenses.map((yearly) => (
                        <tr key = {yearly.year}>
                            <td>{cleanText(yearly.year)}</td>
                            <td>{formatMoney(yearly.income)}</td>
                            <td>{formatMoney(yearly.savings)}</td>
                            <td>{formatMoney(yearly.debt)}</td>
                            <td>{formatMoney(yearly.insurances)}</td>
                            <td>{formatMoney(yearly.bills)}</td>
                            <td>{formatMoney(yearly.tax)}</td>
                            <td>{formatMoney(yearly.parentsAllowances)}</td>
                            <td>{formatMoney(yearly.transport)}</td>
                            <td>{formatMoney(yearly.food)}</td>
                            <td>{formatMoney(yearly.groceries)}</td>
                            <td>{formatMoney(yearly.haircut)}</td>
                            <td>{formatMoney(yearly.household)}</td>
                            <td>{formatMoney(yearly.medical)}</td>
                            <td>{formatMoney(yearly.wants)}</td>
                            <td>{formatMoney(yearly.others)}</td>
                            <td>{formatMoney(yearly.tithes)}</td>
                            <td>{formatMoney(yearly.overspent)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}