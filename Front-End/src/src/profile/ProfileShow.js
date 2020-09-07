// in profile/ShowProfile.jso
import React from 'react';
import { Show, TextInput, SimpleForm, required, TextField,
    Edit,
    DateInput,
    EditButton,
    SelectInput,
    ImageField,
    ImageInput,
    TabbedShowLayout,
    Tab,
    ReferenceManyField,
    Datagrid,
    ShowButton,
    DeleteButton,
    ReferenceField,TopToolbar} from 'react-admin';
import { CustomImageField } from "../fields/CustomImageField";

const ProfileActions = ({ basePath, data, resource, props }) => {
   
    return (
      <TopToolbar>
        <EditButton basePath={basePath} record={data} />
        <DeleteButton basePath={basePath} record={data} />
      </TopToolbar>
    );
  };
  

const ProfileShow = ({ staticContext, ...props }) => {
    return (
        <Show 
        id="my-profile"
        /*
            For the same reason, I need to provide the resource and basePath props
            which are required by the Edit component
        */
        resource="profile"
        basePath="/profile"
        /*
            I also customized the page title as it'll make more sense to the user
        */
        title="My profile"
        redirect={false} // I don't need any redirection here, there's no list page
        {...props} actions={<ProfileActions />}>
        <TabbedShowLayout>
          <Tab label="Profile">
            <CustomImageField source="picture" title="Picture" />
            <TextField label="name" source="name" />
            <TextField label="Phome Number" source="phoneNumber" />
            <TextField label="Date Of Birth" source="dateOfBirth" />
            <TextField label="City" source="city" />
            <TextField label="Country" source="country" />
          </Tab>
          <Tab label="Goals">
            <ReferenceManyField
              label="Goals"
              reference="goals"
              target="id"
              filter={{ method: "getGoalsFromProfile" }}
            >
              <Datagrid>
                <TextField source="title" />
                <TextField source="description" />
                <ShowButton />
              </Datagrid>
            </ReferenceManyField>
          </Tab>
        </TabbedShowLayout>
      </Show>
    );
};

export default ProfileShow;

