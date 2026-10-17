import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import YearlyTable from "../components/tables/YearlyTable";
import "../styles/dashboards/YearlyDashboard.css";

export default function YearlyDashboard () {
    const [yearly, setYearly] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        setError("");
        axios.get("http://localhost:8080/yearly")
        .then((res) => setYearly(res.data))
        .catch((error) => {
            setError(
                error.response?.data?.message ||
                "Failed to retrieve summary records."
            );
        }).finally(() => setLoading(false));
    }, []);

    const downloadExcel = async () => {
        try {
            const response = 
            await fetch("http://localhost:8080/yearly/export");
            if (!response.ok) {
                throw new Error("Failed to download file");
            }

            const blob = 
            await response.blob();
            const url = 
            window.URL
            .createObjectURL(blob);
            const link = 
            document
            .createElement("a");
            link.href = url;
            link.download = 
            "Yearly Expense.xlsx";

            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            setError(err.message || 
            "Download failed");
        }
    };

    return (
        <div className = "yearlyDashboard-center">
        <h1>View Yearly Expense Records</h1>
        {loading ? (
            <p>Loading...</p>
        ) : error ? (
            <div className = 
            "error-message" 
            role = "alert">
                {error}
            </div>
        ) : (
            <>
                {yearly.length === 0 ? (
                    <p>No yearly expense records found.</p>
                ) : (
                    <YearlyTable yearlyExpenses = {yearly}/>
                )}
                <div className = "buttons-center">
                <button className = "custom-btn" 
                onClick = {downloadExcel}>
                    Download
                </button>
                <button className = "custom-btn" 
                onClick = {() => navigate("/monthly/retrieve")}>
                    View Monthly
                </button>
                <button className = "custom-btn" 
                onClick = {() => navigate("/home")}>
                    Home
                </button>
                </div>
            </>
        )}   
        </div>
    );
}