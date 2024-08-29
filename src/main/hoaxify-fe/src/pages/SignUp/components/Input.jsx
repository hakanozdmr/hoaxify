export function Input(props) {
  const { id, label, error, onChange ,type} = props;
  return (
    <div className="mb-3">
      <label htmlFor={id} className="form-label">
        {label}
      </label>
      <input
        className={error ? "form-control is-invalid" : "form-control"}
        id={id}
        type={type}
        onChange={onChange}
      />
      <div className="invalid-feedback">{error}</div>
    </div>
  );
}
