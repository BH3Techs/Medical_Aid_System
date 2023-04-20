import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlans } from 'app/shared/model/plans.model';
import { getEntities as getPlans } from 'app/entities/plans/plans.reducer';
import { IPlanBillingCycle } from 'app/shared/model/plan-billing-cycle.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';
import { DateConfiguration } from 'app/shared/model/enumerations/date-configuration.model';
import { getEntity, updateEntity, createEntity, reset } from './plan-billing-cycle.reducer';

export const PlanBillingCycleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plans.entities);
  const planBillingCycleEntity = useAppSelector(state => state.planBillingCycle.entity);
  const loading = useAppSelector(state => state.planBillingCycle.loading);
  const updating = useAppSelector(state => state.planBillingCycle.updating);
  const updateSuccess = useAppSelector(state => state.planBillingCycle.updateSuccess);
  const periodUnitValues = Object.keys(PeriodUnit);
  const dateConfigurationValues = Object.keys(DateConfiguration);

  const handleClose = () => {
    navigate('/plan-billing-cycle');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...planBillingCycleEntity,
      ...values,
      plans: plans.find(it => it.id.toString() === values.plans.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          periodUnit: 'DAY',
          dateConfiguration: 'DYNAMIC',
          ...planBillingCycleEntity,
          plans: planBillingCycleEntity?.plans?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.planBillingCycle.home.createOrEditLabel" data-cy="PlanBillingCycleCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.planBillingCycle.home.createOrEditLabel">
              Create or edit a PlanBillingCycle
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="plan-billing-cycle-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.planBillingCycle.periodUnit')}
                id="plan-billing-cycle-periodUnit"
                name="periodUnit"
                data-cy="periodUnit"
                type="select"
              >
                {periodUnitValues.map(periodUnit => (
                  <option value={periodUnit} key={periodUnit}>
                    {translate('medicalAidSystemApp.PeriodUnit.' + periodUnit)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.planBillingCycle.periodValue')}
                id="plan-billing-cycle-periodValue"
                name="periodValue"
                data-cy="periodValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.planBillingCycle.dateConfiguration')}
                id="plan-billing-cycle-dateConfiguration"
                name="dateConfiguration"
                data-cy="dateConfiguration"
                type="select"
              >
                {dateConfigurationValues.map(dateConfiguration => (
                  <option value={dateConfiguration} key={dateConfiguration}>
                    {translate('medicalAidSystemApp.DateConfiguration.' + dateConfiguration)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.planBillingCycle.billingDate')}
                id="plan-billing-cycle-billingDate"
                name="billingDate"
                data-cy="billingDate"
                type="text"
              />
              <ValidatedField
                id="plan-billing-cycle-plans"
                name="plans"
                data-cy="plans"
                label={translate('medicalAidSystemApp.planBillingCycle.plans')}
                type="select"
              >
                <option value="" key="0" />
                {plans
                  ? plans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plan-billing-cycle" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PlanBillingCycleUpdate;
