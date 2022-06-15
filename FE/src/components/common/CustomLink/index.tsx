import { Link } from 'react-router-dom';

interface CustomLinkProps {
  path: string;
  component: JSX.Element;
}

const CustomLink = ({ path, component }: CustomLinkProps) => {
  return <Link to={path}>{component}</Link>;
};

export default CustomLink;
