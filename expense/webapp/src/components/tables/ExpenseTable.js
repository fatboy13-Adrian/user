import "../../styles/tables/ExpenseTable.css";

function cleanText(text) {
    if (!text) return "-";
    const trimmed = text.toString().trim();
    return trimmed.length === 0 ? "-" : trimmed;
}

function formatMoney(value) {
    if (value === null || value === undefined || value === "") return "-";
    return `$${Number(value).toFixed(2)}`;
}

export default function ExpenseTable({expenses = []}) {
    return (
        <div className = "expense-table-wrapper">
            <table className = "expense-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Parent's Allowances</th>
                        <th>Transport</th>
                        <th>Food</th>
                        <th>Income</th>
                        <th>Groceries</th>
                        <th>Haircut</th>
                        <th>Household</th>
                        <th>Medical</th>
                    </tr>
                </thead>

                <tbody>
                    {(expenses || []).map((expense) => (
                        <tr key = {expense.date}>
                            <td>{cleanText(expense.date)}</td>
                            <td>{formatMoney(expense.parentsAllowances)}</td>
                            <td>{formatMoney(expense.transport)}</td>
                            <td>{formatMoney(expense.food)}</td>
                            <td>{formatMoney(expense.income)}</td>
                            <td>{formatMoney(expense.groceries)}</td>
                            <td>{formatMoney(expense.haircut)}</td>
                            <td>{formatMoney(expense.household)}</td>
                            <td>{formatMoney(expense.medical)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}