import "../styles/TextField.css";

export default function TextField ({
id, 
name, 
type = "text", 
value, onChange, 
autoComplete, 
disabled = false, 
required = false,
className = ""}) {
    return (
        <input 
            id = {id} 
            name = {name} 
            type = {type} 
            value = {value} 
            onChange = {onChange} 
            disabled = {disabled} 
            autoComplete = {autoComplete} 
            required = {required}
            className = {`form-input ${className}`
            .trim()}
        />
    );
}