import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Button from "../components/Button";
import TextField from "../components/TextField";
import SelectDate from "../components/selections/SelectDate";
import "../styles/ExpenseForm.css";

const API_URL = "http://localhost:8080/expenses";

/*
 * Return today's date in YYYY-MM-DD format.
 */
const getTodayDate = () => 
new Date()
.toISOString()
.slice(0, 10);

/*
 * Create an empty expense object
 * Retain supplied date when resetting form.
 */
const saveEmptyExpense = 
(date = getTodayDate()) => ({
    date,
    income: "",

    //Savings
    emergency: "",
    srs: "",
    ssb: "",

    //Debt
    debt: "",

    //Insurances
    primeLife: "",
    criticare: "",
    termProtector: "",

    //Bills
    electricity: "",
    internet: "",
    mobile: "",
    mortgage: "",

    //Tax
    incomeTax: "",
    propertyTax: "",

    //Everyday spending
    parentsAllowances: "",
    transport: "",
    food: "",
    groceries: "",
    haircut: "",
    household: "",
    medical: "",

    //Wants
    eatingOut: "",
    holiday: "",
    recreational: "",
    shopping: "",
    sports: "",
    tech: "",

    //Others & tithes
    wake: "",
    wedding: "",
    tithes: "",
});

/*
 * Define expense fields in groups
 * This configuration is reused when rendering form
 * & converting numerical fields for backend.
 */
const EXPENSE_GROUPS = [
    {
        title: "Savings",
        fields: [
            {
                name: "emergency",
                label: "Emergency Fund",
                type: "number",
            },
            {
                name: "srs",
                label: "SRS",
                type: "number",
            },
            {
                name: "ssb",
                label: "SSB",
                type: "number",
            },
        ],
    },

    {
        title: "Debt",
        fields: [
            {
                name: "debt",
                label: "Debt",
                type: "number",
            },
        ],
    },

    {
        title: "Insurances",
        fields: [
            {
                name: "primeLife",
                label: "Prime Life",
                type: "number",
            },
            {
                name: "criticare",
                label: "Critical Care",
                type: "number",
            },
            {
                name: "termProtector",
                label: "Term Protector",
                type: "number",
            },
        ],
    },

    {
        title: "Bills",
        fields: [
            {
                name: "electricity",
                label: "Electricity",
                type: "number",
            },
            {
                name: "internet",
                label: "Internet",
                type: "number",
            },
            {
                name: "mobile",
                label: "Mobile",
                type: "number",
            },
            {
                name: "mortgage",
                label: "Mortgage",
                type: "number",
            },
        ],
    },

    {
        title: "Tax",
        fields: [
            {
                name: "incomeTax",
                label: "Income Tax",
                type: "number",
            },
            {
                name: "propertyTax",
                label: "Property Tax",
                type: "number",
            },
        ],
    },

    {
        title: "Everyday Spending",
        fields: [
            {
                name: "parentsAllowances",
                label: "Parent's Allowances",
                type: "number",
            },
            {
                name: "transport",
                label: "Transport",
                type: "number",
            },
            {
                name: "food",
                label: "Food",
                type: "number",
            },
            {
                name: "groceries",
                label: "Groceries",
                type: "number",
            },
            {
                name: "haircut",
                label: "Haircut",
                type: "number",
            },
            {
                name: "household",
                label: "Household",
                type: "number",
            },
            {
                name: "medical",
                label: "Medical",
                type: "number",
            },
        ],
    },

    {
        title: "Wants",
        fields: [
            {
                name: "eatingOut",
                label: "Eating Out",
                type: "number",
            },
            {
                name: "holiday",
                label: "Holiday",
                type: "number",
            },
            {
                name: "recreational",
                label: "Recreational",
                type: "number",
            },
            {
                name: "shopping",
                label: "Shopping",
                type: "number",
            },
            {
                name: "sports",
                label: "Sports",
                type: "number",
            },
            {
                name: "tech",
                label: "Tech",
                type: "number",
            },
        ],
    },

    {
        title: "Others & Tithes",
        fields: [
            {
                name: "wake",
                label: "Wake",
                type: "number",
            },
            {
                name: "wedding",
                label: "Wedding",
                type: "number",
            },
            {
                name: "tithes",
                label: "Tithes",
                type: "number",
            },
        ],
    },
];

/*
 * Collect all numerical field names.
 * Income is handled separately because it is not part
 * of the expense category groups.
 */
const NUMERIC_FIELDS = [
    "income",
    ...EXPENSE_GROUPS.flatMap((group) =>
        group.fields.map((field) => field.name)
    ),
];

/*
 * Convert a field value to a number.
 * Empty or invalid values default to zero.
 */
const toNumberOrZero = 
(value) => {
    if (
        value === "" ||
        value === null ||
        value === undefined
    ) {
        return 0;
    }

    const number = 
    Number(value);
    return Number
    .isFinite(number) 
    ? number : 0;
};

export default function ExpenseForm() {
    const navigate = useNavigate();

    /*
     * Form modes:
     * save   = create a new expense
     * update = update an existing expense
     */
    const [mode, setMode] = 
    useState("save");
    const [expense, setExpense] = 
    useState(() => createEmptyExpense());

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    /*
     * Clear error & success messages.
     */
    const clearMessages = () => {
        setError("");
        setSuccess("");
    };

    /*
     * Handle changes to form fields.
     */
    const handleInputChange = 
    ({target: {name, value} }) => {
        setExpense((currentExpense) => 
        ({
            ...currentExpense,
            [name]: value,
        }));

        /*
         * Changing date means user must
         * load record again before editing.
         */
        if (name === "date") {
            setMode("save");
            clearMessages();
        }
    };

    /*
     * Build request payload for backend.
     */
    const buildPayload = () => ({
        ...expense,
        ...Object.fromEntries(
            NUMERIC_FIELDS
            .map((fieldName) => [
                fieldName,
                toNumberOrZero(expense[fieldName]),
            ])
        ),

        date: expense
        .date.slice(0, 10),
    });

    /*
     * Retrieve an expense using selected date.
     * If a record exists: Switch to edit mode.
     * If no record exists: Switch to save mode.
     */
    const handleLoadRecord = async () => {
        if (!expense.date) {
            setError("Please select a date.");
            setSuccess("");
            return;
        }

        const selectedDate = 
        expense.date.slice(0, 10);
        setLoading(true);
        clearMessages();

        try {
            const response = 
            await axios.get(
            `${API_URL}/date/${selectedDate}`);

            const record =
            Array.isArray(response.data)
            ? response.data[0]
            : response.data;

            /*
             * No matching record was returned.
             */
            if (!record) {
                setMode("save");
                setExpense(
                saveEmptyExpense(selectedDate));
                setError(
                "No record exists for this date. " +
                "You can create a new record.");
                return;
            }

            /*
             * An existing record was found.
             */
            setExpense({
            ...saveEmptyExpense(selectedDate),
            ...record,
            date: selectedDate,
            });

            setMode("edit");
            setSuccess("Existing expense record loaded.");
        } catch (requestError) {
            /*
             * HTTP 404 means no record exists.
             */
            if (requestError.response?.status === 404) {
                setMode("save");
                setExpense(saveEmptyExpense(selectedDate));
                setError(
                "No record exists for this date. " +
                "You can create a new record.");
            } else {
                setError(
                requestError.response?.data?.message ||
                "Unable to retrieve expense record.");
            }
        } finally {
            setLoading(false);
        }
    };

    /*
     * Save a new expense or edit an existing one.
     */
    const handleSubmit = 
    async (event) => {
        event.preventDefault();
        if (!expense.date) {
            setError("Please select a date.");
            setSuccess("");
            return;
        }

        setLoading(true);
        clearMessages();
        try {
            const payload = 
            buildPayload();

            /*
             * Save
             */
            if (mode === "save") {
                await axios.post(
                `${API_URL}/save`,
                payload);

                setSuccess(
                "Expense record created successfully.");

                /*
                 * Subsequent submissions will update
                 * this record rather than create it again.
                 */
                setMode("edit");
            }

            /*
             * Edit
             */
            else {
                await axios.put(
                `${API_URL}/date/${payload.date}`,
                payload);
                setSuccess(
                "Expense record updated successfully.");
            }
        } catch (requestError) {
            /*
             * Handle a duplicate record when saving.
             */
            if (mode === "save" &&
            requestError.response?.status === 409) {
                setError(
                "A record already exists for this date. " +
                "Click Load Record to update it.");
            } else {
                setError(
                requestError.response?.data?.message ||
                requestError.message ||
                `Failed to ${
                    mode === "save" ? "create" : "update"
                } expense record.`
                );
            }
        } finally {
            setLoading(false);
        }
    };

    /*
     * Discard current form & return to
     * daily expense dashboard.
     */
    const handleDiscard = 
    () => {
        navigate("/dailyExpenses/retrieve");
    };

    /*
     * Navigate to home page.
     */
    const handleHome = 
    () => {
        navigate("/home");
    };

    return (
        <main className = "expense-page container-centered">
            <section className = "form-wrapper">
                <h2 className = "expense-form-title">
                    {mode === "save"
                    ? "Save New Expense Record"
                    : "Edit Existing Expense Record"}
                </h2>

                {/* Error message */}
                {error && (
                    <div
                    className="alert alert-danger"
                    role="alert">
                        {error}
                    </div>
                )}

                {/* Success message */}
                {success && (
                    <div
                    className="alert alert-success"
                    role="status">
                        {success}
                    </div>
                )}

                {/* Date selection & record loading */}
                <div className = "date-row">
                    <SelectDate
                        name = "date"
                        value = {expense.date}
                        onChange = {handleInputChange}
                        disabled = {loading}/>

                    <Button
                        type = "button"
                        onClick = {handleLoadRecord}
                        disabled = {loading}>
                        {loading
                        ? "Loading..."
                        : "Load Record"}
                    </Button>
                </div>

                {/* Current form mode */}
                <div className = "alert alert-info">
                    {mode === "save"
                        ? "Save mode — enter your expenses & click Save."
                        : `Edit mode — record loaded for ${expense.date}.`}
                </div>

                <form
                    onSubmit = {handleSubmit}
                    noValidate>
                    {/*Income*/}
                    <div className = "expense-field">
                        <TextField
                            name = "income"
                            label = "Income"
                            type = "number"
                            value = {expense.income}
                            onChange = {handleInputChange}
                            disabled = {loading}
                            className = "mb-3 full-width"/>
                    </div>

                    {/*Expense categories*/}
                    {EXPENSE_GROUPS.map((group) => {
                        const groupId = `group-${group.title
                        .toLowerCase()
                        .replace(/[^a-z0-9]+/g, "-")}`;

                        return (
                            <section
                                key = {group.title}
                                className = "expense-group"
                                aria-labelledby={groupId}>
                                <h3 id = {groupId}>
                                    {group.title}
                                </h3>

                                <div className = "field-group-container">
                                    {group.fields.map((field) => (
                                        <TextField
                                            key = {field.name}
                                            name = {field.name}
                                            label = {field.label}
                                            type = {field.type}
                                            value = {
                                                expense[field.name] ?? ""
                                            }
                                            onChange = {handleInputChange}
                                            disabled = {loading}
                                            className = "mb-3 full-width"
                                        />
                                    ))}
                                </div>
                            </section>
                        );
                    })}

                    {/*Form actions*/}
                    <div className = "button-wrapper">
                        <Button
                            type = "submit"
                            disabled = {loading}>
                            {loading
                            ? "Saving..."
                            : mode === "save"
                            ? "Save"
                            : "Update"}
                        </Button>

                        <Button
                            type = "button"
                            onClick = {handleDiscard}
                            disabled = {loading}>
                            Discard
                        </Button>

                        <Button
                            type = "button"
                            onClick = {handleHome}
                            disabled = {loading}>
                            Home
                        </Button>
                    </div>
                </form>
            </section>
        </main>
    );
}