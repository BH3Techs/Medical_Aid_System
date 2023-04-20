import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlanBillingCycle } from 'app/shared/model/plan-billing-cycle.model';
import { getEntities as getPlanBillingCycles } from 'app/entities/plan-billing-cycle/plan-billing-cycle.reducer';
import { IPlans } from 'app/shared/model/plans.model';
import { getEntities as getPlans } from 'app/entities/plans/plans.reducer';
import { IPolicy } from 'app/shared/model/policy.model';
import { PolicyStatus } from 'app/shared/model/enumerations/policy-status.model';
import { getEntity, updateEntity, createEntity, reset } from './policy.reducer';

export const PolicyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const planBillingCycles = useAppSelector(state => state.planBillingCycle.entities);
  const plans = useAppSelector(state => state.plans.entities);
  const policyEntity = useAppSelector(state => state.policy.entity);
  const loading = useAppSelector(state => state.policy.loading);
  const updating = useAppSelector(state => state.policy.updating);
  const updateSuccess = useAppSelector(state => state.policy.updateSuccess);
  const policyStatusValues = Object.keys(PolicyStatus);

  const handleClose = () => {
    navigate('/policy');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPlanBillingCycles({}));
    dispatch(getPlans({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...policyEntity,
      ...values,
      planBillingCycle: planBillingCycles.find(it => it.id.toString() === values.planBillingCycle.toString()),
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
          status: 'ACTIVE',
          ...policyEntity,
          planBillingCycle: policyEntity?.planBillingCycle?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.policy.home.createOrEditLabel" data-cy="PolicyCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.policy.home.createOrEditLabel">Create or edit a Policy</Translate>
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
                  id="policy-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.policyNumber')}
                id="policy-policyNumber"
                name="policyNumber"
                data-cy="policyNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.suffix')}
                id="policy-suffix"
                name="suffix"
                data-cy="suffix"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.pricingGroup')}
                id="policy-pricingGroup"
                name="pricingGroup"
                data-cy="pricingGroup"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.nextOfKin')}
                id="policy-nextOfKin"
                name="nextOfKin"
                data-cy="nextOfKin"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.memberIdentifier')}
                id="policy-memberIdentifier"
                name="memberIdentifier"
                data-cy="memberIdentifier"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.parentPolicy')}
                id="policy-parentPolicy"
                name="parentPolicy"
                data-cy="parentPolicy"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.sponsorIdentifier')}
                id="policy-sponsorIdentifier"
                name="sponsorIdentifier"
                data-cy="sponsorIdentifier"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.sponsorType')}
                id="policy-sponsorType"
                name="sponsorType"
                data-cy="sponsorType"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.status')}
                id="policy-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {policyStatusValues.map(policyStatus => (
                  <option value={policyStatus} key={policyStatus}>
                    {translate('medicalAidSystemApp.PolicyStatus.' + policyStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.policy.balance')}
                id="policy-balance"
                name="balance"
                data-cy="balance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="policy-planBillingCycle"
                name="planBillingCycle"
                data-cy="planBillingCycle"
                label={translate('medicalAidSystemApp.policy.planBillingCycle')}
                type="select"
              >
                <option value="" key="0" />
                {planBillingCycles
                  ? planBillingCycles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/policy" replace color="info">
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

export default PolicyUpdate;
