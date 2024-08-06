import { axiosAPI } from "../../index"

export type eventType = "Festival" | "Workshop" | "Show" | "Talk" | string;

export interface EventDTO {
    id:number,
    address:string,
    description:string,
    finalDate:string,
    initialDate:string,
    maximumCapacity:number,
    name:string,
    type:eventType
}

export interface SaveEventDTO {
    address:string,
    description:string,
    finalDate:string,
    initialDate:string,
    maximumCapacity:number,
    name:string,
    type:eventType
}

export interface SubEventDTO {
    id:number,
    description:string,
    hours:string,
    location:string,
    name:string,
    type:eventType,
    event:EventDTO
}

let config:object = {
    headers: {
        'Accept': 'application/json;',
        'Content-Type': 'application/json',
    }
}

export async function apiGetEventList() {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/event/list', config);
}

export async function apiGetEventById(eventId:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get(`/event/${eventId}`, config);
}


export async function apiEventRegistration(saveEventDTO:SaveEventDTO) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.post(`/event/${saveEventDTO}`, config);
}

export async function apiGetSubEventByEventId(eventId:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get(`/subEvent/list/${eventId}`, config);
}

