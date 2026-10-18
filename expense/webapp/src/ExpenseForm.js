import React, {useEffect, useState, useRef} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import "./styles/ExpenseForm.css";

const API_URL = "http://localhost:8080/expenses";
const expenseFields = [
    {
        title: "Savings",
        fields: [
            {name: "emergency", label: "Emergency"},
            {name: "srs", label: "SRS"},
            {name: "ssb", label: "SSB"}
        ]
    },
    {
        title: "Insurances",
        fields: [
            {name: "primeLife", label: "AIA Prime Life"},
            {name: "criticare", label: "HSBC CritiCare"},
            {name: "termProtector", label: "HSBC Term"}
        ]
    },
    {
        title: "Bills & Housing",
        fields: [
            {name: "electricity", label: "Electricity"},
            {name: "internet", label: "Internet"},
            {name: "mobile", label: "Mobile"},
            {name: "mortgage", label: "Mortgage"}
        ]
    },
    {
        title: "Tax",
        fields: [
            {name: "incomeTax", label: "Income Tax"},
            {name: "propertyTax", label: "Property Tax"}
        ]
    },
    {
        title: "Everyday Expenses",
        fields: [
            {name: "parentsAllowances", label: "For Parents"},
            {name: "transport", label: "Transport"},
            {name: "food", label: "Food"},
            {name: "groceries", label: "Groceries"},
            {name: "haircut", label: "Haircut"},
            {name: "household", label: "Household"},
            {name: "medical", label: "Medical"}
        ]
    },
    {
        title: "Wants",
        fields: [
            {name: "eatingOut", label: "Eating Out"},
            {name: "holiday", label: "Holiday"},
            {name: "recreational", label: "Recreational"},
            {name: "shopping", label: "Shopping"},
            {name: "sports", label: "Sports"},
            {name: "tech", label: "Tech"}
        ]
    },
    {
        title: "Others",
        fields: [
            {name: "wake", label: "Wake"},
            {name: "wedding", label: "Wedding"},
            {name: "tithes", label: "Tithes"}
        ]
    }
];

const numericFields = [
    "income",
    ...expenseFields.flatMap((section) =>
    section.fields.map((field) => 
    field.name)
    )
];

function getToday() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1)
    .padStart(2, "0");
    const day = String(today.getDate())
    .padStart(2, "0");
    return `${year}-${month}-${day}`;
}

function saveEmptyExpense(date = getToday()) {
    const expense = 
    {date, income: "0"};
    numericFields.forEach((field) => {
        if (field !== "income") 
        expense[field] = "0";
   });
    return expense;
}

export default function ExpenseForm() {
    const navigate = useNavigate();
    const [expense, setExpense] = 
    useState(() => saveEmptyExpense());
    const [mode, setMode] = 
    useState("new");
    const [loading, setLoading] = 
    useState(false);
    const [saving, setSaving] = 
    useState(false);
    const [error, setError] = 
    useState("");
    const [success, setSuccess] = 
    useState("");
    const justSaved = useRef(false);

    useEffect(() => {
        if (!expense.date) return;
        if (justSaved.current) {
            justSaved.current = false;
            return;
       }

        let cancelled = false;
        const loadExpense = async () => {
            setLoading(true);
            setError("");
            setSuccess("");
            try {
                const response = 
                await axios
                .get(`${API_URL}/date/${expense.date}`);
                if (cancelled) return;
                const data = response.data;
                const loadedExpense = 
                saveEmptyExpense(data.date);
                numericFields.forEach((field) => {
                    loadedExpense[field] = 
                    data[field] == null ? "0" : 
                    String(data[field]);
               });
                setExpense(loadedExpense);
                setMode("edit");
           } catch (err) {
                if (cancelled) return;
                if (err.response?.status === 404) {
                    setExpense((current) => ({
                    ...saveEmptyExpense(current.date)}));
                    setMode("new");
               } else {
                    setError("Failed to retrieve the expense record.");
               }
           } finally {
                if (!cancelled) 
                setLoading(false);
           }
       };

        loadExpense();
        return () => 
        {cancelled = true;};
   }, [expense.date]);

    const handleDateChange = (event) => {
        const date = event.target.value;
        justSaved.current = false;
        setExpense(saveEmptyExpense(date));
        setMode("new");
        setError("");
        setSuccess("");
   };

    const handleChange = (event) => {
        const {name, value} = 
        event.target;
        setExpense((current) => 
        ({...current, [name]: value}));
        setError("");
        setSuccess("");
   };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");
        setSuccess("");
        if (!expense.date) {
            setError("Please select an expense date.");
            return;
       }

        for (const field of numericFields) {
            const value = expense[field];
            if (value === "" || value === null || 
            value === undefined || 
            !Number.isFinite(Number(value)) || 
            Number(value) < 0) {
                setError(`Please enter a valid amount for 
                ${field === "income" ? "Income" : field.replaceAll("_", " ")}.`);
                return;
           }
       }

        const payload = {date: expense.date};
        numericFields.forEach((field) => 
        {payload[field] = Number(expense[field]);});
        setSaving(true);
        justSaved.current = true;

        try {
            await axios.post(`${API_URL}/save`, payload);
            setSuccess("Expense record saved successfully.");
            navigate("/expenses/dates");
       } catch (err) {
            justSaved.current = false;
            setError(err.response?.data?.message || 
            "Failed to save the expense record.");
       } finally {
            setSaving(false);
       }
   };

    return (
        <div className = "container-centered">
            <div className = "form-wrapper">
                <h2 className = "text-center">
                    {mode === "new" ? 
                    "New Daily Expense Record" : 
                    "Edit Existing Expense Record"}
                </h2>
                
                {error && <div className = "alert alert-danger" 
                role = "alert">{error}</div>}
                {success && <div className = 
                "alert alert-success">{success}</div>}
                {loading && <div className = 
                "alert alert-info">Loading...</div>}

                <form onSubmit = {handleSubmit}>
                    <div className = "date-row">
                        <div className = "expenseForm-group">
                            <label>Date</label>
                            <input type = "date" value = {expense.date} 
                            onChange = {handleDateChange} disabled = {saving}/>
                        </div>
                        <div className = "expenseForm-group">
                            <label>Income</label>
                            <input type="number" name="income" 
                            value = {expense.income} 
                            onChange = {handleChange} 
                            step = "0.01" disabled = {saving}/>
                        </div>
                        <div className = "expenseForm-group">
                            <label>Debt</label>
                            <input type = "number" name = "debt" 
                            value = {expense.debt} onChange = {handleChange} 
                            step = "0.01" disabled = {saving}/>
                        </div>
                    </div>

                    {expenseFields.map((section) => (
                        <div key = {section.title} className="mb-3">
                            <h3>{section.title}</h3>
                            <div className = "field-group-container">
                                {section.fields.map((field) => (
                                    <div key = {field.name} className =
                                    "expenseForm-group">
                                        <label>{field.label}</label>
                                        <input type = "number" name = {field.name} 
                                        value = {expense[field.name] || "0"} 
                                        onChange = {handleChange} step = "0.01" 
                                        disabled = {saving}/>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}

                    <div className = "button-wrapper">
                        <button type = "submit" 
                        disabled = {saving || loading} 
                        className = "btn-save">
                            {saving ? "Saving..." : "Save"}
                        </button>
                        <button type = "button" onClick = {() => navigate("/expenses/dates")} 
                        disabled = {saving} className = "btn-cancel">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}