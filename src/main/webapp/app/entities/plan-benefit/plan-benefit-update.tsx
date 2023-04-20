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
import { IBenefitType } from 'app/shared/model/benefit-type.model';
import { getEntities as getBenefitTypes } from 'app/entities/benefit-type/benefit-type.reducer';
import { IPlanBenefit } from 'app/shared/model/plan-benefit.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';
import { getEntity, updateEntity, createEntity, reset } from './plan-benefit.reducer';

export const PlanBenefitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plans.entities);
  const benefitTypes = useAppSelector(state => state.benefitType.entities);
  const planBenefitEntity = useAppSelector(state => state.planBenefit.entity);
  const loading = useAppSelector(state => state.planBenefit.loading);
  const updating = useAppSelector(state => state.planBenefit.updating);
  const updateSuccess = useAppSelector(state => state.planBenefit.updateSuccess);
  const periodUnitValues = Object.keys(PeriodUnit);

  const handleClose = () => {
    navigate('/plan-benefit');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
    dispatch(getBenefitTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...planBenefitEntity,
      ...values,
      plans: plans.find(it => it.id.toString() === values.plans.toString()),
      benefitType: benefitTypes.find(it => it.id.toString() === values.benefitType.toString()),
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
          waitingPeriodUnit: 'DAY',
          ...planBenefitEntity,
          plans: planBenefitEntity?.plans?.id,
          benefitType: planBenefitEntity?.benefitType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.planBenefit.home.createOrEditLabel" data-cy="PlanBenefitCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.planBenefit.home.createOrEditLabel">Create or edit a PlanBenefit</Translate>
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
                  id="plan-benefit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.planBenefit.name')}
                id="plan-benefit-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.planBenefit.waitingPeriodUnit')}
                id="plan-benefit-waitingPeriodUnit"
                name="waitingPeriodUnit"
                data-cy="waitingPeriodUnit"
                type="select"
              >
                {periodUnitValues.map(periodUnit => (
                  <option value={periodUnit} key={periodUnit}>
                    {translate('medicalAidSystemApp.PeriodUnit.' + periodUnit)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.planBenefit.waitingPeriodValue')}
                id="plan-benefit-waitingPeriodValue"
                name="waitingPeriodValue"
                data-cy="waitingPeriodValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.planBenefit.description')}
                id="plan-benefit-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.planBenefit.active')}
                id="plan-benefit-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                id="plan-benefit-plans"
                name="plans"
                data-cy="plans"
                label={translate('medicalAidSystemApp.planBenefit.plans')}
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
              <ValidatedField
                id="plan-benefit-benefitType"
                name="benefitType"
                data-cy="benefitType"
                label={translate('medicalAidSystemApp.planBenefit.benefitType')}
                type="select"
              >
                <option value="" key="0" />
                {benefitTypes
                  ? benefitTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plan-benefit" replace color="info">
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

export default PlanBenefitUpdate;
