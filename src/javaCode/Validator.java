package javaCode;

import javaCode.superUser.addElements;
//If new user is incorrect, e.g. name is numbers the Exception will be thrown but the user still registers.
//Need to stop the user from registering and "try again"
public class Validator {
    addElements addelements= new addElements();

    public static boolean name(String name){
        return name.matches("([a-zA-ZæøåÆØÅ]+['\\-,. ]?)+");
    }

    public static boolean phone(String phone){
        return phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*");
    }

    public static boolean email(String email){
        return email.matches(".[\\S]+@.[\\S]+[.].[\\S]+");
    }
    //Input validation for car objects
    public static boolean carId(String id){
        int idCounter = 0;
        if(id.matches("[0-9]+")){
            for(int i = 0; i < Lists.getCars().size(); i++){
                if(idCounter<=Integer.parseInt(Lists.getCars().get(i).getCarID())){
                    idCounter=Integer.parseInt(Lists.getCars().get(i).getCarID());
                }
            }
            if(idCounter >= Integer.parseInt(id)){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    public static boolean carType(String type){
        return type.matches(".{2,10}");
    }

    public static boolean carDescription(String description){
        return description.matches(".{2,50}");
    }

    public static boolean carPrice(int price){
        return price>0 && price<1_000_000;
    }


    //Components
    public static boolean carIdComponents(String ID){
        for(int i = 0; i < Lists.getCars().size(); i++){
            if(Lists.getCars().get(i).getCarID().equals(ID)){
                return true;
            }
        }
        return false;
    }
    public static boolean componentId(String ID){
        String[] splitInnId = ID.split("-");
        int highestcomponentType = 0;
        int highestcomponentTypeId = 0;

        for(int i = 0; i < Lists.getComponents().size();i++){
            String [] splitExistingId = Lists.getComponents().get(i).getComponentID().split("-");
            String componentId = Lists.getComponents().get(i).getComponentID();
            if(componentId.equals(ID)){
                return false;
            }
            else if(Integer.parseInt(splitInnId[0])<0){
                return false;
            }
            else{
                if(splitInnId[0].equals(splitExistingId[0])){
                    //Finds highest componentId after "-" in component id-number
                    for(int j = 0; j <Lists.getComponents().size();j++){
                        if(splitInnId[0].equals(Lists.getComponents().get(j).getComponentID().charAt(0))){
                            if(highestcomponentTypeId <= Integer.parseInt(splitExistingId[1])){
                                highestcomponentTypeId = Integer.parseInt(splitExistingId[1]);
                            }
                        }
                    }
                    if(Integer.parseInt(splitInnId[1]) > highestcomponentTypeId){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean componentType(String type){
        if(type.length()==0){
            throw new IllegalArgumentException("The components type is not defined");
        }
        else{
            for(Component i : Lists.getComponents()){
                if(i.getComponentType().equals(type)){
                    return true;
                }
            }
            if(Dialogs.showChooseDialog("This component type is not defined. Do you want to add a new component?")){
                addElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents(),"", "", "", 0);
                return false;
            }
        }
        return true;
    }
    public static boolean componentPrice(int price){
        return price>0;
    }
}
