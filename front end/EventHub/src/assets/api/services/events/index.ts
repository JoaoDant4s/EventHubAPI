import { axiosAPI } from "../../index"

export interface EventDTO {
    id:number,
    address:string,
    description:string,
    finalDate:string,
    initialDate:string,
    maximumCapacity:number,
    name:string,
    type:string
}

export interface SubEventDTO {
    id:number,
    hours:string,
    location:string,
    name:string,
    type:string
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

