import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPolicy } from 'app/shared/model/policy.model';
import { getEntities as getPolicies } from 'app/entities/policy/policy.reducer';
import { IServiceProvider } from 'app/shared/model/service-provider.model';
import { getEntities as getServiceProviders } from 'app/entities/service-provider/service-provider.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { ClaimStatus } from 'app/shared/model/enumerations/claim-status.model';
import { getEntity, updateEntity, createEntity, reset } from './claim.reducer';

export const ClaimUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const policies = useAppSelector(state => state.policy.entities);
  const serviceProviders = useAppSelector(state => state.serviceProvider.entities);
  const claimEntity = useAppSelector(state => state.claim.entity);
  const loading = useAppSelector(state => state.claim.loading);
  const updating = useAppSelector(state => state.claim.updating);
  const updateSuccess = useAppSelector(state => state.claim.updateSuccess);
  const claimStatusValues = Object.keys(ClaimStatus);

  const handleClose = () => {
    navigate('/claim');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPolicies({}));
    dispatch(getServiceProviders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...claimEntity,
      ...values,
      policy: policies.find(it => it.id.toString() === values.policy.toString()),
      serviceProvider: serviceProviders.find(it => it.id.toString() === values.serviceProvider.toString()),
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
          claimStatus: 'PENDING',
          ...claimEntity,
          policy: claimEntity?.policy?.id,
          serviceProvider: claimEntity?.serviceProvider?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.claim.home.createOrEditLabel" data-cy="ClaimCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.claim.home.createOrEditLabel">Create or edit a Claim</Translate>
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
                  id="claim-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.submissionDate')}
                id="claim-submissionDate"
                name="submissionDate"
                data-cy="submissionDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.approvalDate')}
                id="claim-approvalDate"
                name="approvalDate"
                data-cy="approvalDate"
                type="date"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.processingDate')}
                id="claim-processingDate"
                name="processingDate"
                data-cy="processingDate"
                type="date"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.claimStatus')}
                id="claim-claimStatus"
                name="claimStatus"
                data-cy="claimStatus"
                type="select"
              >
                {claimStatusValues.map(claimStatus => (
                  <option value={claimStatus} key={claimStatus}>
                    {translate('medicalAidSystemApp.ClaimStatus.' + claimStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.diagnosis')}
                id="claim-diagnosis"
                name="diagnosis"
                data-cy="diagnosis"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.claimant')}
                id="claim-claimant"
                name="claimant"
                data-cy="claimant"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.claim.relationshipToMember')}
                id="claim-relationshipToMember"
                name="relationshipToMember"
                data-cy="relationshipToMember"
                type="text"
              />
              <ValidatedField
                id="claim-policy"
                name="policy"
                data-cy="policy"
                label={translate('medicalAidSystemApp.claim.policy')}
                type="select"
              >
                <option value="" key="0" />
                {policies
                  ? policies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-serviceProvider"
                name="serviceProvider"
                data-cy="serviceProvider"
                label={translate('medicalAidSystemApp.claim.serviceProvider')}
                type="select"
              >
                <option value="" key="0" />
                {serviceProviders
                  ? serviceProviders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim" replace color="info">
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

export default ClaimUpdate;
