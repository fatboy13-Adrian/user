import "../../styles/tables/ExpenseTable.css";

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

export default function ExpenseTable ({expenses = []}) {
    /*
    Extract only specific variables 
    to expose in this table
    */
    const restrictedExpenses = 
    (expenses || [])
    .map(expense => ({
        date: expense.date,

        /*
        Whitelist: restrict data down to
        only the properties you want to see
        */
        transport: expense.transport,
        food: expense.food,
        groceries: expense.groceries,
        haircut: expense.haircut,
        household: expense.household,
        medical: expense.medical,
        eatingOut: expense.eatingOut,
        hoilday: expense.hoilday,
        recreational: expense.recreational,
        shopping: expense.shopping,
        sports: expense.sports,
        tech: expense.tech        
    }))

    return (
        <><div className="expense-table-wrapper"></div>
        <table className="expense-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>transport</th>
                    <th>Food</th>
                    <th>Groceries</th>
                    <th>Haircut</th>
                    <th>Household</th>
                    <th>Medical</th>
                    <th>Eating Out</th>
                    <th>Holiday</th>
                    <th>Recreational</th>
                    <th>Shopping</th>
                    <th>Sports</th>
                    <th>Tech</th>
                </tr>
            </thead>

            <tbody>
                {restrictedExpenses
                .map((expense) =>  (
                    <tr key = {expense
                    .date}>
                        <td>{cleanText(expense
                        .date)}</td>
                        <td>{formatMoney(expense
                        .transport)}</td>
                        <td>{formatMoney(expense
                        .food)}</td>
                        <td>{formatMoney(expense
                        .groceries)}</td>
                        <td>{formatMoney(expense
                        .haircut)}</td>
                        <td>{formatMoney(expense
                        .household)}</td>
                        <td>{formatMoney(expense
                        .medical)}</td>
                        <td>{formatMoney(expense
                        .eatingOut)}</td>
                        <td>{formatMoney(expense
                        .hoilday)}</td>
                        <td>{formatMoney(expense
                        .recreational)}</td>
                        <td>{formatMoney(expense
                        .shopping)}</td>
                        <td>{formatMoney(expense
                        .sports)}</td>
                        <td>{formatMoney(expense
                        .tech)}</td>
                    </tr>
                ))}
            </tbody>
        </table></>
    );
}