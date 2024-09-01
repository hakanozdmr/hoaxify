export function Alert(props) {
    const{children, styleType} = props;
    return <div className={`alert alert-${styleType || 'success'} m-3`}>{children}</div>
}
