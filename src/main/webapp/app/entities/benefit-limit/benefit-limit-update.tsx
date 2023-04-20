import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBenefitLimitType } from 'app/shared/model/benefit-limit-type.model';
import { getEntities as getBenefitLimitTypes } from 'app/entities/benefit-limit-type/benefit-limit-type.reducer';
import { IPlanBenefit } from 'app/shared/model/plan-benefit.model';
import { getEntities as getPlanBenefits } from 'app/entities/plan-benefit/plan-benefit.reducer';
import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';
import { getEntity, updateEntity, createEntity, reset } from './benefit-limit.reducer';

export const BenefitLimitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const benefitLimitTypes = useAppSelector(state => state.benefitLimitType.entities);
  const planBenefits = useAppSelector(state => state.planBenefit.entities);
  const benefitLimitEntity = useAppSelector(state => state.benefitLimit.entity);
  const loading = useAppSelector(state => state.benefitLimit.loading);
  const updating = useAppSelector(state => state.benefitLimit.updating);
  const updateSuccess = useAppSelector(state => state.benefitLimit.updateSuccess);
  const periodUnitValues = Object.keys(PeriodUnit);

  const handleClose = () => {
    navigate('/benefit-limit');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBenefitLimitTypes({}));
    dispatch(getPlanBenefits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...benefitLimitEntity,
      ...values,
      benefitLimitType: benefitLimitTypes.find(it => it.id.toString() === values.benefitLimitType.toString()),
      planBenefit: planBenefits.find(it => it.id.toString() === values.planBenefit.toString()),
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
          limitPeriodUnit: 'DAY',
          ...benefitLimitEntity,
          benefitLimitType: benefitLimitEntity?.benefitLimitType?.id,
          planBenefit: benefitLimitEntity?.planBenefit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.benefitLimit.home.createOrEditLabel" data-cy="BenefitLimitCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.benefitLimit.home.createOrEditLabel">Create or edit a BenefitLimit</Translate>
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
                  id="benefit-limit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitLimit.limitValue')}
                id="benefit-limit-limitValue"
                name="limitValue"
                data-cy="limitValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitLimit.limitPeriodUnit')}
                id="benefit-limit-limitPeriodUnit"
                name="limitPeriodUnit"
                data-cy="limitPeriodUnit"
                type="select"
              >
                {periodUnitValues.map(periodUnit => (
                  <option value={periodUnit} key={periodUnit}>
                    {translate('medicalAidSystemApp.PeriodUnit.' + periodUnit)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitLimit.limitPeriodValue')}
                id="benefit-limit-limitPeriodValue"
                name="limitPeriodValue"
                data-cy="limitPeriodValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.benefitLimit.active')}
                id="benefit-limit-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                id="benefit-limit-benefitLimitType"
                name="benefitLimitType"
                data-cy="benefitLimitType"
                label={translate('medicalAidSystemApp.benefitLimit.benefitLimitType')}
                type="select"
              >
                <option value="" key="0" />
                {benefitLimitTypes
                  ? benefitLimitTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="benefit-limit-planBenefit"
                name="planBenefit"
                data-cy="planBenefit"
                label={translate('medicalAidSystemApp.benefitLimit.planBenefit')}
                type="select"
              >
                <option value="" key="0" />
                {planBenefits
                  ? planBenefits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/benefit-limit" replace color="info">
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

export default BenefitLimitUpdate;
