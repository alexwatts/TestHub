import React from "react";
import classNames from "classnames";
import {HoverOverImageIconProps} from "../types";
import styles from "../Result.module.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCamera } from '@fortawesome/free-solid-svg-icons'


export function HoverOverImageIcon(props: HoverOverImageIconProps) {

    function openImageTab (data: string) {
        const image = new Image();
        image.src = data;
        const w = window.open("");
        w?.document.write(image.outerHTML);
    }

    return (
            <div className={
                classNames(
                    styles.hover_img,
                )}>
                {props.image &&
                    <a href="#/" onClick={() => openImageTab('data:image/png;base64,' + props.image?.data)} >
                        <FontAwesomeIcon icon={faCamera}/>
                        <span>
                            <img
                                src={'data:image/png;base64,' + props.image.data}
                                width={400}
                                height={200}
                                alt={'screenshot'}
                            />
                        </span>
                    </a>}
            </div>
    );
}
