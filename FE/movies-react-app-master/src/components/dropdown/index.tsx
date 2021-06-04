import React from "react";

interface Props {
    label: string,
    items: string[] | number[],
    value: string | number,
    onChange: (e: React.ChangeEvent) => void
}

export default function Dropdown(props: Props) {
    const {
        label,
        items,
        value,
        onChange
    } = props;

    return (
        <div className="d-inline-flex flex-grow-1 m-1">
            <label className="mr-1" htmlFor={`${label}-select`}>{label}</label>

            <select
                className="form-control form-control-sm w-100"
                id={`${label}-select`}
                // @ts-ignore
                onChange={onChange}
                value={value}
            >
                {
                    // @ts-ignore
                    items.map((item: string | number) => (
                        <option
                            key={item}
                            value={item}
                        >
                            {item}
                        </option>
                    ))
                }
            </select>
        </div>
    );
}