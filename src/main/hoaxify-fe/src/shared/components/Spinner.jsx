export function Spinner(props){
    const {size} = props;

    return(
        <span className={`spinner-border ${size ? `spinner-border-${size}` : '' } ` } aria-hidden="true"></span>
    )
}