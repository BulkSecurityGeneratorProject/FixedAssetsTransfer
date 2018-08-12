import { Moment } from 'moment';

export const enum AssetActionType {
    DISPOSAL = 'DISPOSAL',
    RETURN = 'RETURN',
    SALE = 'SALE'
}

export const enum TechnicalReview {
    IT = 'IT',
    ADMINSTRAITIVE_AFFAIRS = 'ADMINSTRAITIVE_AFFAIRS',
    MAINTENANCE = 'MAINTENANCE'
}

export interface IFixedAssets {
    id?: number;
    assets_no?: number;
    serial_no?: number;
    description?: string;
    date?: Moment;
    asset_action_type?: AssetActionType;
    source?: string;
    destination?: string;
    manager_approval?: boolean;
    fixed_assets_manager_approval?: boolean;
    technical_review?: TechnicalReview;
    technical_commentary?: string;
    it_asset_previewer?: string;
    it_asset_previewer_name?: string;
    administraitve_affairs_previewer?: string;
    administraitive_affairs_job_name?: string;
    technical_maintenance_commentry?: string;
    technical_maintenance_previewer?: string;
    technical_maintenance_job_name?: string;
}

export class FixedAssets implements IFixedAssets {
    constructor(
        public id?: number,
        public assets_no?: number,
        public serial_no?: number,
        public description?: string,
        public date?: Moment,
        public asset_action_type?: AssetActionType,
        public source?: string,
        public destination?: string,
        public manager_approval?: boolean,
        public fixed_assets_manager_approval?: boolean,
        public technical_review?: TechnicalReview,
        public technical_commentary?: string,
        public it_asset_previewer?: string,
        public it_asset_previewer_name?: string,
        public administraitve_affairs_previewer?: string,
        public administraitive_affairs_job_name?: string,
        public technical_maintenance_commentry?: string,
        public technical_maintenance_previewer?: string,
        public technical_maintenance_job_name?: string
    ) {
        this.manager_approval = false;
        this.fixed_assets_manager_approval = false;
    }
}
