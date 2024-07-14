import {FC} from "react";
import BasicProps from "@/component/basicComponent/basicProps";
import styles from './basic.module.css';


const BasicComponent:FC<BasicProps> = ( {children, className, style, ...props} ) => {
    return (
        <div className={`${className} ${styles.main}`} style={style} {...props}>
            {children}
        </div>
    )
}

export default BasicComponent